package com.aryan.fitnessapp_test;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
public class Category implements Parcelable {
    private int ID;
    private String categoryName;
    private Bitmap backgroundImage;


    public Category(int ID,String categoryName,Bitmap backgroundImage){
        this.ID=ID;
        this.categoryName=categoryName;
        this.backgroundImage=backgroundImage;
    }
    protected Category(Parcel in) {
        ID = in.readInt();
        categoryName = in.readString();
        backgroundImage = in.readParcelable(Bitmap.class.getClassLoader());
    }





    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeString(categoryName);
        dest.writeParcelable(backgroundImage, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
    public String getCategoryName(){
        return categoryName;
    }
    public int getID(){
        return ID;
    }
    public Bitmap getBackgroundImage(){
        return backgroundImage;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setBackgroundImage(Bitmap backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
