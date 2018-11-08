package com.aryan.fitnessapp_test.Networking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aryan.fitnessapp_test.Adapters.CategoryRecyclerView;
import com.aryan.fitnessapp_test.AppExecutor;
import com.aryan.fitnessapp_test.R;
import com.aryan.fitnessapp_test.WorkoutDatabase;
import com.aryan.fitnessapp_test.WorkoutProgress;
import com.aryan.fitnessapp_test.Workouts;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Aryan Soni on 10/17/2018.
 */

public class WorkoutInfoFetch {
    public WorkoutInfoFetch(){

    }
    public static void fetchWorkoutInfo(String url, final onFetchTaskCompleted l,final Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    List<Workouts> workoutsList= Arrays.asList(gson.fromJson(String.valueOf(jsonArray),Workouts[].class));
                    l.onFetchWorkoutInfoFetch(workoutsList);
                    Log.e("name",""+workoutsList.get(1).getName_original());
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, R.string.volley_error,Toast.LENGTH_SHORT).show();

            }
        });
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
    public  static void fetchWorkoutImage(String url, final onFetchImageCompleted l1, final Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject large = jsonObject.getJSONObject("large_cropped");
                    String imageUrl = large.getString("url");
                    l1.onFetchImageUrlCompleted(imageUrl);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Image not Available",Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }
    public static void FavWorkout(final WorkoutDatabase mDb,Context context,final Workouts workouts){
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.workoutDAO().insertWorkouts(workouts);
            }
        });
    }
    public static void DeleteFavWorkout(final WorkoutDatabase mDb,Context context,final Workouts workouts){
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.workoutDAO().deleteTask(workouts);
            }
        });
    }
    public static void SaveWorkoutProgress(final WorkoutDatabase mDb,Context ctx,final WorkoutProgress workoutProgress){
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.workoutDAO().insertWorkoutProgress(workoutProgress);
            }
        });
    }
    public static boolean isConnected() throws InterruptedException, IOException {
        final String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }
//    public static Float getOverallDuration(final WorkoutDatabase mDb, Context ctx, final WorkoutProgress workoutProgress, final int id){
//        final Float duration;
//        AppExecutor.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                duration =mDb.workoutDAO().getSumDuration(id);
//            }
//        });
//        return duration;
//    }

}
