package com.aryan.fitnessapp_test;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by Aryan Soni on 10/27/2018.
 */

@Database(entities = {Workouts.class,WorkoutProgress.class},version = 2,exportSchema = false)
public abstract class WorkoutDatabase extends RoomDatabase{
    private static final String LOG_TAG=WorkoutDatabase.class.getSimpleName();
    private  static final Object LOCK = new Object();
    private static final String DATABASE_NAME ="workoutsdatabase";
    private static WorkoutDatabase sInstance;
    public static WorkoutDatabase getsInstance(Context context){
        if(sInstance==null){
            synchronized (LOCK){
                Log.d(LOG_TAG, context.getString(R.string.creating_db_instance));
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        WorkoutDatabase.class
                        ,WorkoutDatabase.DATABASE_NAME).allowMainThreadQueries()//Only for getting sum of the column rest all are done on background thread
                        .build();


            }
        }
        Log.d(LOG_TAG, context.getString(R.string.getting_db_tag));
        return sInstance;

    }
    public abstract WorkoutDAO workoutDAO();
}
