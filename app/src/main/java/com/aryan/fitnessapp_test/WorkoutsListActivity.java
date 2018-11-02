package com.aryan.fitnessapp_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.aryan.fitnessapp_test.Adapters.CustomItemClickListener;
import com.aryan.fitnessapp_test.Adapters.WorkoutsRecyclerView;
import com.aryan.fitnessapp_test.Networking.WorkoutInfoFetch;
import com.aryan.fitnessapp_test.Networking.onFetchTaskCompleted;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkoutsListActivity extends AppCompatActivity {

    @BindView(R.id.workouts_recyclerview)RecyclerView mWorkoutRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_workouts_list);
        ButterKnife.bind(this);
        int catid = getIntent().getExtras().getInt("i");
        Log.e("ID transfer",""+catid);
        String url="https://wger.de/api/v2/exercise/?category="+catid+"&format=json&language=2&limit=20&ordering=id";
        WorkoutInfoFetch.fetchWorkoutInfo(url, new onFetchTaskCompleted() {
            @Override
            public void onFetchWorkoutInfoFetch(final List<Workouts> workouts) {

                Log.e("size",""+workouts.size());
                mWorkoutRecyclerView.setLayoutManager(new LinearLayoutManager(WorkoutsListActivity.this));

                WorkoutsRecyclerView workoutsRecyclerView = new WorkoutsRecyclerView(workouts, new CustomItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent = new Intent(WorkoutsListActivity.this,WorkoutDetailActivity.class);
                        Workouts workouts1 = workouts.get(position);
                        intent.putExtra(getString(R.string.workouts_clicked_parcel),workouts1);
                        startActivity(intent);

                    }
                });

                mWorkoutRecyclerView.setAdapter(workoutsRecyclerView);

            }
        },WorkoutsListActivity.this);



    }

}
