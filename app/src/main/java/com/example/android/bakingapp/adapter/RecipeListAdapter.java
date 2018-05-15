package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeDetail;
import com.example.android.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Erzeugt von M. Fengels am 15.05.2018.
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeHolder> {
	
	private ArrayList<Recipe> mData;
	private LayoutInflater mInflater;
	private Context mContext;
	
	public RecipeListAdapter(ArrayList<Recipe> data, Context context){
		mData = data;
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}
	
	@NonNull
	@Override
	public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView;
		switch (viewType){
			case 0:
				itemView = mInflater.inflate(R.layout.recipe_list_item_left,parent,false);
				break;
			case 1:
				itemView = mInflater.inflate(R.layout.no_connection,parent,false);
				break;
			default:
				itemView = mInflater.inflate(R.layout.recipe_list_item_right,parent,false);
		}
		
		return new RecipeHolder(itemView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
		Recipe recipe = mData.get(position);
		holder.mName.setText(recipe.getName());
		if(!recipe.getImage().isEmpty()){
			Picasso.with(mContext).load(Uri.parse(recipe.getImage())).error(R.drawable.ic_no_photo).into(holder.mRecipeImage);
		}
	}
	
	@Override
	public int getItemViewType(int position) {
		if(mData == null){
			return 2;
		}
		return position%2;
	}
	
	@Override
	public int getItemCount() {
		if(mData == null){
			return 1;
		}
		return mData.size();
	}
	
	public void refreshData(ArrayList<Recipe> data) {
		mData = data;
		notifyDataSetChanged();
	}
	
	class RecipeHolder extends RecyclerView.ViewHolder {
		ImageView mRecipeImage;
		TextView mName;
		
		RecipeHolder(View itemView) {
			super(itemView);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,RecipeDetail.class);
					intent.putExtra("Recipe",(Serializable) mData.get(getAdapterPosition()));
					mContext.startActivity(intent);
				}
			});
			mRecipeImage = itemView.findViewById(R.id.recipe_image);
			mName = itemView.findViewById(R.id.recipe_name);
		}
	}
}
