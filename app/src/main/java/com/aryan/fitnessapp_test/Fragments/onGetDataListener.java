package com.aryan.fitnessapp_test.Fragments;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Aryan Soni on 11/4/2018.
 */

public interface  onGetDataListener
{
    void onSuccess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}
