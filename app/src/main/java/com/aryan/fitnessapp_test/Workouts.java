package com.aryan.fitnessapp_test;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Aryan Soni on 10/17/2018.
 */

@Entity(tableName = "Workouts",indices = {@Index(value = "id",unique = true)})
public class Workouts implements Parcelable{

   @PrimaryKey
    private int id;
    private int category;
    private  String description;
    private String name_original;



    public Workouts(int category, String description, String name_original,int id) {
        this.category = category;
        this.description = description;
        this.name_original = name_original;
        this.id = id;
    }

    protected Workouts(Parcel in) {
        category = in.readInt();
        description = in.readString();
        name_original = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(category);
        dest.writeString(description);
        dest.writeString(name_original);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Workouts> CREATOR = new Creator<Workouts>() {
        @Override
        public Workouts createFromParcel(Parcel in) {
            return new Workouts(in);
        }

        @Override
        public Workouts[] newArray(int size) {
            return new Workouts[size];
        }
    };

    public int getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getName_original() {
        return name_original;
    }
    public void setName_original(String name_original){
        this.name_original = name_original;
    }

    public int getId() {
        return id;
    }

    public void setId(int ID) {
        this.id = ID;
    }
}
