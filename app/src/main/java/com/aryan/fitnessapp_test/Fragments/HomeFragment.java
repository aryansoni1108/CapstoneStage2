package com.aryan.fitnessapp_test.Fragments;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.aryan.fitnessapp_test.R;
import com.aryan.fitnessapp_test.WorkoutUser;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends Fragment {
    @BindView(R.id.height_edit_text)EditText height_edit_text;
    @BindView(R.id.weight_edit_text)EditText weight_edit_text;
    @BindView(R.id.bmi_button)Button bmi_btx;
    @BindView(R.id.bmi_text_view)TextView bmi_txt_view;
    @BindView(R.id.chart)
    GraphView chart;
    public static final String TAG = HomeFragment.class.getSimpleName();
    ArrayList<Float> bmiArrayList = new ArrayList<>();
    ArrayList<String> timestampArrayList = new ArrayList<>();

    String height;
    String weight;
    Float bmi;
    DatabaseReference databaseReference;
    public static final String FIREBASE_CHILD_WORKOUTS = "workoutinfo";
    SimpleDateFormat sdf = new SimpleDateFormat("yy:MM:dd");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        databaseReference=FirebaseDatabase.getInstance().getReference(FIREBASE_CHILD_WORKOUTS);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = null;

            if(user==null) {
                Toast.makeText(getActivity(), "Not Logged in", Toast.LENGTH_SHORT);
            }
            else {
                ref = FirebaseDatabase.getInstance().getReference(FIREBASE_CHILD_WORKOUTS).child(user.getUid());

            }


        bmi_btx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weight=weight_edit_text.getText().toString();
                height=height_edit_text.getText().toString();
                try {
                    bmi=(Float.valueOf(weight)/Float.valueOf(height))/Float.valueOf(height);
                }catch (NumberFormatException e){
                    bmi=0f;
                }

                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String timeStamp = String.valueOf(System.currentTimeMillis() / 1000L);
                final WorkoutUser workoutUser = new WorkoutUser(timeStamp,weight,height,String.valueOf(bmi));

                Toast.makeText(getActivity(), ""+bmi, Toast.LENGTH_SHORT).show();
                databaseReference.child(userId).child(timeStamp).setValue(workoutUser);


            }
        });
        if(ref!=null) {
            readData(ref, new onGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    Log.e(TAG, "FINISHED");

                }

                @Override
                public void onStart() {
                    Log.e(TAG, "STARTED");

                }

                @Override
                public void onFailure() {

                }
            });
        }
        return view;
    }
    public float[] getFloats(ArrayList<Float> values) {
        float[] floatArray = new float[values.size()];
        int i = 0;

        for (Float f : values) {
            floatArray[i++] = (f != null ? f : Float.NaN); // Or whatever default you want.
        }
        return floatArray;
    }
    public void readData(DatabaseReference ref, final onGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds :dataSnapshot.getChildren()) {

                    WorkoutUser workoutUser = ds.getValue(WorkoutUser.class);
                    if (workoutUser != null) {
                        Log.i("Bmi", workoutUser.getBmi());
                        bmiArrayList.add(Float.valueOf(workoutUser.getBmi()));
                        timestampArrayList.add(workoutUser.getId());
                        Log.e("timestampArraySize",""+timestampArrayList.size());
                        Log.e("bmiSize",""+bmiArrayList.size());

                    }


                }
                float[] f =getFloats(bmiArrayList);





                DataPoint[] dataPoints = new DataPoint[timestampArrayList.size()];
                for (int i=0;i<timestampArrayList.size();i++){
                    Date time = new java.util.Date(Long.parseLong(timestampArrayList.get(i)) * 1000);
                    dataPoints[i]=new DataPoint(time,(double) bmiArrayList.get(i));
                }
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                Collections.sort(timestampArrayList);
                series.setTitle("BMI history");
                chart.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if(isValueX){
                            return sdf.format(new Date ((long)value));
                        }
                        else {
                            return super.formatLabel(value, isValueX);
                        }
                    }
                });
                chart.addSeries(series);
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError firebaseError) {
                listener.onFailure();
            }
        });

    }



}
