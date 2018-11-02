package com.aryan.fitnessapp_test.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.aryan.fitnessapp_test.WorkoutDatabase;
import com.aryan.fitnessapp_test.Workouts;

/**
 * Created by Aryan Soni on 10/27/2018.
 */

public class AddWorkoutViewModel extends ViewModel {
    private LiveData<Workouts> workout;
    public AddWorkoutViewModel(WorkoutDatabase database, int id){
        workout = database.workoutDAO().loadTaskById(id);
    }
    public LiveData<Workouts> getworkout(){
        return workout;
    }
}
