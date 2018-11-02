package com.aryan.fitnessapp_test.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.aryan.fitnessapp_test.WorkoutDatabase;

/**
 * Created by Aryan Soni on 10/27/2018.
 */

public class AddWorkoutModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final WorkoutDatabase database;
    private final int mWorkoutId;
    public AddWorkoutModelFactory(WorkoutDatabase database,int mWorkoutId){
        this.database=database;
        this.mWorkoutId=mWorkoutId;

    }
    @NonNull


    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AddWorkoutViewModel(database,mWorkoutId);
    }
}
