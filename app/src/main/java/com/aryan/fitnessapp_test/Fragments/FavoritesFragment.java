package com.aryan.fitnessapp_test.Fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aryan.fitnessapp_test.Adapters.CustomItemClickListener;
import com.aryan.fitnessapp_test.Adapters.WorkoutsRecyclerView;
import com.aryan.fitnessapp_test.R;
import com.aryan.fitnessapp_test.ViewModels.MainViewModel;
import com.aryan.fitnessapp_test.WorkoutDetailActivity;
import com.aryan.fitnessapp_test.Workouts;
import com.aryan.fitnessapp_test.WorkoutsListActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesFragment extends Fragment {

    @BindView(R.id.fav_recycleView)RecyclerView recyclerView;

    public FavoritesFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this,view);
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getWorkouts_saved().observe(this, new Observer<List<Workouts>>() {
            @Override
            public void onChanged(@Nullable final List<Workouts> workouts) {
                Log.e("size",""+workouts.size());
                populateUi(workouts);

                           }
        });


        return view;
    }
    public void populateUi(final List<Workouts> workouts){
        WorkoutsRecyclerView workoutsRecyclerView = new WorkoutsRecyclerView(workouts, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(),WorkoutDetailActivity.class);
                if(workouts==null){
                    Toast.makeText(getActivity(),"No favorite exercises",Toast.LENGTH_SHORT).show();
                }
                else {
                    Workouts workouts1 = workouts.get(position);

                    intent.putExtra(getString(R.string.workouts_clicked_parcel), workouts1);
                    startActivity(intent);
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(workoutsRecyclerView);

    }


    // TODO: Rename method, update argument and hook method into UI event



}
