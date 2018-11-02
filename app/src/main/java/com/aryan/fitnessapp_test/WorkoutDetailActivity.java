package com.aryan.fitnessapp_test;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.aryan.fitnessapp_test.Networking.WorkoutInfoFetch;
import com.aryan.fitnessapp_test.Networking.onFetchImageCompleted;
import com.aryan.fitnessapp_test.ViewModels.MainViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkoutDetailActivity extends AppCompatActivity {
    Workouts workouts;
    @BindView(R.id.workout_description)TextView workout_description;
    @BindView(R.id.place_image)ImageView imageView;
    @BindView(R.id.titleview)TextView titleView;
    @BindView(R.id.start_stop)ToggleButton button;
    @BindView(R.id.save_fab)FloatingActionButton save_fab;
    CountDownTimer countDownTimer;
    long time = 12000;
    Boolean isCounterRunning = false;
    Boolean isitthere = true;
    WorkoutDatabase mDb;
    Context context=WorkoutDetailActivity.this;
    @BindView(R.id.stop_button)Button StopButton;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);
        ButterKnife.bind(this);
        mDb=WorkoutDatabase.getsInstance(this);
        workouts = getIntent().getParcelableExtra(getResources().getString(R.string.workouts_clicked_parcel));
        if(!Objects.equals(workouts.getDescription(), "")) {
            workout_description.setText(workouts.getDescription());
        }
        else{
            workout_description.setText(R.string.no_description_string);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.isChecked()) {
                    reverseTimer(time, button);
                    isCounterRunning=true;
                }
                else {
                    countDownTimer.cancel();
                    button.setText(""+formatTime(time));
                    isCounterRunning=false;
                }
            }
        });
        StopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCounterRunning){
                    countDownTimer.cancel();
                    time=12000;

                    button.setText("START");
                    button.setChecked(false);
                }
            }
        });

        titleView.setText(workouts.getName_original());

        int id = workouts.getId();
        final String imageURL = "https://wger.de/api/v2/exerciseimage/"+id+"/thumbnails/";
        Log.e("image id",""+id);
        WorkoutInfoFetch.fetchWorkoutImage(imageURL, new onFetchImageCompleted() {
            @Override
            public void onFetchImageUrlCompleted(String url) {

                Picasso.get().load(url).placeholder(R.drawable.no_image_placeholder).into(imageView);
                Log.e("image url",""+url);
            }
        },WorkoutDetailActivity.this);

        final MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getWorkouts_saved().observe(this, new Observer<List<Workouts>>() {
            @Override
            public void onChanged(@Nullable final List<Workouts> workoutsPojo) {
                Boolean isitthere=false;
                for (int i=0;i<workoutsPojo.size();i++){
                    if (workoutsPojo.get(i).getId()==workouts.getId()){
                        isitthere=true;
                    }
                }
                if(isitthere==false){
                    save_fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            save_fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                            WorkoutInfoFetch.FavWorkout(mDb,context,workouts);
                        }
                    });
                }
                else {
                    save_fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                    save_fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WorkoutInfoFetch.DeleteFavWorkout(mDb,context,workouts);
                            save_fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                        }
                    });
                }


            }
        });

    }
    public void reverseTimer(long miliSeconds,final Button tv){

        countDownTimer=new CountDownTimer(miliSeconds, 1000) {


            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onTick(long millisUntilFinished) {
                time=millisUntilFinished;
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;

                tv.setText("TIME : " + String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
                tv.setBackgroundColor(getColor(R.color.colorPrimaryDark));


            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onFinish() {
                tv.setText("Completed");
                tv.setBackgroundColor(getColor(R.color.colorAccent));
            }
        }.start();
    }
    public String formatTime(long milli){
        int seconds = (int) (milli / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        String formattedTime="TIME : " + String.format("%02d", minutes)
                + ":" + String.format("%02d", seconds);
        return formattedTime;
    }



}
