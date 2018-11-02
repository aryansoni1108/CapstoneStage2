package com.aryan.fitnessapp_test;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Aryan Soni on 10/27/2018.
 */
@Dao
public interface WorkoutDAO {
    @Query("SELECT * FROM Workouts ORDER BY id")
    LiveData<List<Workouts>> loadAllWorkouts();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWorkouts(Workouts workouts);
    @Delete
    void deleteTask(Workouts workouts);
    @Query("SELECT * FROM Workouts WHERE id=:id")
    LiveData<Workouts> loadTaskById(int id);

}
