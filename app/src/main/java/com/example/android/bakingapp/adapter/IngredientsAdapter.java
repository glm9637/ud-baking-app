package com.example.android.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredient;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    LayoutInflater mInflater;
    ArrayList<Ingredient> mData;

    public IngredientsAdapter(LayoutInflater inflater, ArrayList<Ingredient> data){
        mInflater = inflater;
        mData = data;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IngredientsViewHolder(mInflater.inflate(R.layout.ingredient_entry,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        holder.setIngredient(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class IngredientsViewHolder extends RecyclerView.ViewHolder{

        TextView mName;
        TextView mQuantity;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.txt_name);
            mQuantity = itemView.findViewById(R.id.txt_quantity);
        }
	
	    /**
	     * updates the Textviews to the given ingredient
	     * @param ingredient the ingredient to display
	     */
	    public void setIngredient(Ingredient ingredient){
            mName.setText(ingredient.getName());
            mQuantity.setText(String.format("%s %s",ingredient.getQuantity(),ingredient.getMeasure()));
        }
    }

}
