package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.android.bakingapp.adapter.RecipeListAdapter;
import com.example.android.bakingapp.idling_resource.SimpleIdlingResource;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.networking.RecipeLoader;

import java.util.ArrayList;

public class RecipeList extends AppCompatActivity
	implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>{
	
	@Nullable
	private SimpleIdlingResource mIdlingResource;
	
	/**
	 * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
	 */
	@VisibleForTesting
	@NonNull
	public IdlingResource getIdlingResource() {
		if (mIdlingResource == null) {
			mIdlingResource = new SimpleIdlingResource();
		}
		return mIdlingResource;
	}
	
	RecyclerView mList;
	RecipeListAdapter mListAdapter;
	
	/**
	 * sets up the Recyclerview accordingly to if its tablet or not
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_list);

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		mList = findViewById(R.id.recipe_list);
		mListAdapter = new RecipeListAdapter(new ArrayList<Recipe>(),this);
		if(getResources().getBoolean(R.bool.isTablet)){
			GridLayoutManager layoutManager = new GridLayoutManager(this,3);
			mList.setLayoutManager(layoutManager);
		}else {
			mList.setLayoutManager(new LinearLayoutManager(this));
		}
		mList.setAdapter(mListAdapter);
		getSupportLoaderManager().initLoader(0,null,this).forceLoad();
		
		// Get the IdlingResource instance
		getIdlingResource();
	}
	
	
	@NonNull
	@Override
	public android.support.v4.content.Loader<ArrayList<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
		if(mIdlingResource!=null){
			mIdlingResource.setIdleState(false);
		}
		return new RecipeLoader(this);
	}
	
	@Override
	public void onLoadFinished(@NonNull android.support.v4.content.Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
		mListAdapter.refreshData(data);		if(mIdlingResource!=null){
			mIdlingResource.setIdleState(true);
		}
	}
	
	@Override
	public void onLoaderReset(@NonNull android.support.v4.content.Loader<ArrayList<Recipe>> loader) {
	
	}
	
}
