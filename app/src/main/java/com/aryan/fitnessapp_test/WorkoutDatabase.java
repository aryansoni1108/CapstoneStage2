package com.aryan.fitnessapp_test;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

/**
 * Created by Aryan Soni on 10/27/2018.
 */

@Database(entities = {Workouts.class},version = 1,exportSchema = false)
public abstract class WorkoutDatabase extends RoomDatabase{
    private static final String LOG_TAG=WorkoutDatabase.class.getSimpleName();
    private  static final Object LOCK = new Object();
    private static final String DATABASE_NAME ="workoutsdatabase";
    private static WorkoutDatabase sInstance;
    public static WorkoutDatabase getsInstance(Context context){
        if(sInstance==null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new database Instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        WorkoutDatabase.class
                        ,WorkoutDatabase.DATABASE_NAME).build();


            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;

    }
    public abstract WorkoutDAO workoutDAO();
}
