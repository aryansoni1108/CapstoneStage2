package com.aryan.fitnessapp_test.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.aryan.fitnessapp_test.Adapters.CategoryRecyclerView;
import com.aryan.fitnessapp_test.Adapters.CustomItemClickListener;
import com.aryan.fitnessapp_test.Category;
import com.aryan.fitnessapp_test.R;
import com.aryan.fitnessapp_test.WorkoutsListActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowseFragment extends Fragment {
    @BindView(R.id.category_recycler_view)RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER



    public BrowseFragment() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browse, container, false);
        ButterKnife.bind(this, view);
        final List<Category> categories=new ArrayList<>();




                Bitmap abs = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.man_abs);

                Bitmap arms = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.biceps_image);
                Bitmap back = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.man_back_image);
                Bitmap calves = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.calves_image);
                Bitmap chest = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.chest_image);
                Bitmap legs = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.legs_image);
                Bitmap shoulders = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.shoulder_image);
                Category category1 = new Category(10,"Abs",abs);
                Category category2 = new Category(8,"Arms",arms);
                Category category3 = new Category(12,"back",back);
                Category category4 = new Category(14,"calves",calves);
                Category category5 = new Category(11,"chest",chest);
                Category category6 = new Category(9,"legs",legs);
                Category category7 = new Category(13,"Shoulders",shoulders);


                categories.add(category1);
                categories.add(category2);
                categories.add(category3);
                categories.add(category4);
                categories.add(category5);
                categories.add(category6);
                categories.add(category7);











        Log.e("size",""+categories.size());
        CategoryRecyclerView categoryRecyclerView = new CategoryRecyclerView(categories, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                int id = categories.get(position).getID();
                Log.e("clickedId",""+id);
                Category category = categories.get(position);
                Intent intent=new Intent(getActivity(), WorkoutsListActivity.class);
                intent.putExtra("i",id);
                startActivity(intent);

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(categoryRecyclerView);

        return view;
    }









//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
