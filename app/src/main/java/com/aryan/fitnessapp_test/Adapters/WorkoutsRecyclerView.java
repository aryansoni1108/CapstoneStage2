package com.aryan.fitnessapp_test.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aryan.fitnessapp_test.R;
import com.aryan.fitnessapp_test.Workouts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aryan Soni on 10/17/2018.
 */

public class WorkoutsRecyclerView extends RecyclerView.Adapter<WorkoutsRecyclerView.MyViewHolder> {
    private List<Workouts> workouts = new ArrayList<>();
    CustomItemClickListener listener;
    public WorkoutsRecyclerView(List<Workouts> workouts,CustomItemClickListener listener){
        this.workouts=workouts;
        this.listener=listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.workout_item,viewGroup,false);
        final  MyViewHolder myViewHolder=new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, myViewHolder.getAdapterPosition());
                Log.e("clickd",""+myViewHolder.getAdapterPosition());
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(workouts.get(i).getName_original());


    }

    @Override
    public int getItemCount() {
        return workouts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.workout_card_text_view);
        }
    }
}
