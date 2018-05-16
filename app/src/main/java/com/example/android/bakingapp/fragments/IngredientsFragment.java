package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.IngredientsAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.widget.WidgetHelper;

public class IngredientsFragment extends Fragment {

    Recipe mData;
    RecyclerView mRecyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mData = getArguments().getParcelable("Recipe");
        View rootView = inflater.inflate(R.layout.fragment_ingredients,container,false);
        
        mRecyclerView = rootView.findViewById(R.id.rcv_ingredient);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new IngredientsAdapter(inflater,mData.getIngredients()));
        
        Button mWidget = rootView.findViewById(R.id.save_to_widget);
        mWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetHelper.saveToFile(mData,getContext());
                WidgetHelper.sendRefreshBroadcast(getContext());
                Toast.makeText(getContext(),"Widget updated",Toast.LENGTH_LONG).show();
            }
        });
        return rootView;
    }
}
