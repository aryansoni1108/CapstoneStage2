package com.aryan.fitnessapp_test;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aryan Soni on 11/5/2018.
 */
@Entity(tableName = "workoutprogress",indices = {@Index(value = "timestamp",unique = true)})
public class WorkoutProgress implements Parcelable{
    @PrimaryKey
    long timestamp;
    int id;
    String workoutName;
    float durationinmin;

    public WorkoutProgress() {

    }

    protected WorkoutProgress(Parcel in) {
        id = in.readInt();
        workoutName = in.readString();
        durationinmin = in.readInt();
        timestamp=in.readLong();
    }

    public static final Creator<WorkoutProgress> CREATOR = new Creator<WorkoutProgress>() {
        @Override
        public WorkoutProgress createFromParcel(Parcel in) {
            return new WorkoutProgress(in);
        }

        @Override
        public WorkoutProgress[] newArray(int size) {
            return new WorkoutProgress[size];
        }
    };

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public void setDurationinmin(float durationinmin) {
        this.durationinmin = durationinmin;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public int getId() {
        return id;
    }

    public float getDurationinmin() {
        return durationinmin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(workoutName);
        dest.writeFloat(durationinmin);
        dest.writeLong(timestamp);
    }
}


