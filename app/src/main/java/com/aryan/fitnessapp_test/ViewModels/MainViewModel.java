package com.aryan.fitnessapp_test.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.aryan.fitnessapp_test.WorkoutDatabase;

import com.aryan.fitnessapp_test.WorkoutProgress;

import com.aryan.fitnessapp_test.Workouts;

import java.util.List;

/**
 * Created by Aryan Soni on 10/27/2018.
 */

public class MainViewModel extends AndroidViewModel {
    private static  final String TAG = MainViewModel.class.getSimpleName();
    private LiveData<List<Workouts>> workouts_saved;
    private LiveData<List<WorkoutProgress>> workout_progress;
    public MainViewModel(@NonNull Application application){
        super(application);
        WorkoutDatabase workoutDatabase = WorkoutDatabase.getsInstance(this.getApplication());
        Log.d(TAG,"Actively retrieving tasks from database");
        workouts_saved = workoutDatabase.workoutDAO().loadAllWorkouts();
        workout_progress=workoutDatabase.workoutDAO().loadAllWorkoutProgress();

    }
    public LiveData<List<Workouts>> getWorkouts_saved(){
        return workouts_saved;
    }
    public LiveData<List<WorkoutProgress>> getWorkout_progress(){return workout_progress;}
}
