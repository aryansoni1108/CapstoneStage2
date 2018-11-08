package com.aryan.fitnessapp_test;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.aryan.fitnessapp_test.Networking.WorkoutInfoFetch;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

/**
 * Created by Aryan Soni on 11/8/2018.
 */

public class WorkoutIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static final String ACTION_GET_BMI =
            "com.aryan.fitnessapp_test.action.get_bmi";
    private static final String FIREBASE_CHILD_WORKOUTS = "workoutinfo";

    public WorkoutIntentService() {
        super("WorkoutIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_BMI.equals(action)) {
                handleGetBMI();
            }
        }

    }

    public static void startActiongetBmi(Context context) {
        Intent intent = new Intent(context, WorkoutIntentService.class);
        intent.setAction(ACTION_GET_BMI);
        context.startService(intent);
    }
    public static boolean checkAvailability() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user==null){
            return false;
        }
        else {
            return true;
        }


    }

    private void handleGetBMI() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        final int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,NewAppWidget.class));


        if (user != null) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference(FIREBASE_CHILD_WORKOUTS).child(user.getUid());

            ref.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds:dataSnapshot.getChildren()){
                        WorkoutUser workoutUser = ds.getValue(WorkoutUser.class);
                        if (workoutUser!=null) {
                            NewAppWidget.updateRecentBmi(WorkoutIntentService.this, appWidgetManager, String.valueOf(workoutUser.getBmi()), appWidgetIds);
                        }
                        else {
                            Log.e("error","Workouts not found");
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else {
            Log.e("WorkoutIntentService","User not Logged in");
        }

    }
}
