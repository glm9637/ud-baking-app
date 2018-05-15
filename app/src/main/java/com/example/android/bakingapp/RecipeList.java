package com.example.android.bakingapp;

import android.content.Loader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.android.bakingapp.adapter.RecipeListAdapter;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.networking.RecipeLoader;

import java.util.ArrayList;

public class RecipeList extends AppCompatActivity
	implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>{
	
	RecyclerView mList;
	RecipeListAdapter mListAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_list);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		mList = findViewById(R.id.recipe_list);
		mListAdapter = new RecipeListAdapter(null,this);
		mList.setLayoutManager(new LinearLayoutManager(this));
		mList.setAdapter(mListAdapter);
		getSupportLoaderManager().initLoader(0,null,this).forceLoad();
	}
	
	
	@NonNull
	@Override
	public android.support.v4.content.Loader<ArrayList<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
		return new RecipeLoader(this);
	}
	
	@Override
	public void onLoadFinished(@NonNull android.support.v4.content.Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
		mListAdapter.refreshData(data);
	}
	
	@Override
	public void onLoaderReset(@NonNull android.support.v4.content.Loader<ArrayList<Recipe>> loader) {
	
	}
	
}
