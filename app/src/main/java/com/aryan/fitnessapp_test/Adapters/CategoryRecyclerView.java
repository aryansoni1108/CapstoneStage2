package com.aryan.fitnessapp_test.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aryan.fitnessapp_test.Category;
import com.aryan.fitnessapp_test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aryan Soni on 10/16/2018.
 */

public class CategoryRecyclerView extends RecyclerView.Adapter<CategoryRecyclerView.MyViewHolder> {
    List<Category> categories = new ArrayList<>();
    CustomItemClickListener listener;

    public CategoryRecyclerView(List<Category> categories,CustomItemClickListener listener){
        this.categories=categories;
        this.listener=listener;

    }
    @NonNull
    @Override
    public CategoryRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        final  MyViewHolder myViewHolder=new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecyclerView.MyViewHolder myViewHolder, int i) {

      myViewHolder.textView.setText(categories.get(i).getCategoryName());
      myViewHolder.imageView.setImageBitmap(categories.get(i).getBackgroundImage());

    }

    @Override
    public int getItemCount() {
        Log.e("size",""+categories.size());
        return categories.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.category_card_text_view);

            imageView = itemView.findViewById(R.id.category_card_back_image);
        }
    }


}
