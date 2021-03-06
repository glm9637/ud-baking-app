package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.bakingapp.adapter.BottomNavigationAdapter;
import com.example.android.bakingapp.fragments.IngredientsFragment;
import com.example.android.bakingapp.fragments.StepFragment;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;

public class RecipeDetail extends AppCompatActivity {
	
	Recipe mRecipe;
	TextView mStepperPage;
	TextView mCurrentStep;
	ImageView mPreviousStep;
	ImageView mNextStep;
	LinearLayout mBottomSheet;
	BottomSheetBehavior sheetBehavior;
	RecyclerView mNavigation;
	BottomNavigationAdapter mAdapter;
	IngredientsFragment mIngredients;
	StepFragment mStepFragment;
	FragmentManager mFragmentManager;
	Boolean isOnTablet;
	
	/**
	 * sets up all Views and Fragments
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_detail);
		mRecipe = getIntent().getParcelableExtra("Recipe");
		mStepperPage = findViewById(R.id.txt_stepper);
		mCurrentStep = findViewById(R.id.txt_current_page);
		mBottomSheet = findViewById(R.id.bottom_sheet);
		mPreviousStep = findViewById(R.id.btn_previous);
		mNextStep = findViewById(R.id.btn_next);
		isOnTablet = getResources().getBoolean(R.bool.isTablet);
		showSummary(0,"Ingredients");

		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		if(!isOnTablet) {
			sheetBehavior = BottomSheetBehavior.from(mBottomSheet);

			mStepperPage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
						sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
					} else {
						collapseBottomSheet();
					}
				}
			});
		}
		mNavigation = findViewById(R.id.navigation_list);
		mNavigation.setLayoutManager(new LinearLayoutManager(this));
		mAdapter = new BottomNavigationAdapter(this,mRecipe);
		mAdapter.setItemSelectedListener(new BottomNavigationAdapter.ItemSelected() {
			@Override
			public void onIngredientsSelected(int position, Recipe recipe) {
				showSummary(position,getString(R.string.ingredients));
				collapseBottomSheet();
				pushFragment(mIngredients);
			}

			@Override
			public void onStepSelected(int position, Step step) {
				showSummary(position,step.getShortDescription());
				collapseBottomSheet();

				Bundle stepData = new Bundle();
				stepData.putParcelable("Step",step);
				mStepFragment.setArguments(stepData);
				pushFragment(mStepFragment);
				mStepFragment.updateData();
			}
		});
		mNavigation.setAdapter(mAdapter);

		mNextStep.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAdapter.selectNextItem();
			}
		});

		mPreviousStep.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAdapter.selectPreviousItem();
			}
		});

		TextView title = findViewById(R.id.title_text);
		title.setText(mRecipe.getName());
		TextView servings = findViewById(R.id.txt_servings);
		servings.setText(mRecipe.getServings().toString());

		mFragmentManager = getSupportFragmentManager();

		mIngredients = new IngredientsFragment();
		Bundle ingredientdata = new Bundle();
		ingredientdata.putParcelable("Recipe",mRecipe);
		mIngredients.setArguments(ingredientdata);
		pushFragment(mIngredients);

		mStepFragment = new StepFragment();


		if(savedInstanceState!=null){
			mAdapter.selectItem(savedInstanceState.getInt("position"));
			if(!isOnTablet)
				sheetBehavior.setState(savedInstanceState.getInt("sheet state"));
			mStepFragment.onActivityCreated(savedInstanceState);
		}else {
			if(!isOnTablet)
				sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
		}
	}

	/**
	 * shows a summary on top of the bottom sheet
	 * @param position the position of the current Page
	 * @param text the Description of the current page
	 */
	private void showSummary(int position, String text){
		mStepperPage.setText(String.format("%d/%d",++position,mRecipe.getSteps().size() + 1));
		mCurrentStep.setText(text);
	}

	/**
	 * Displays a new Fragment
	 * @param newFragment the Fragment to be displayed.
	 */
	private void pushFragment(Fragment newFragment) {

		FragmentTransaction objFragmentTransaction = mFragmentManager.beginTransaction();
		objFragmentTransaction.replace(R.id.fragment_container, newFragment);
		objFragmentTransaction.commit();

	}
	
	/**
	 * saves the current page
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("position",mAdapter.getCurrentPosition());
		if(!isOnTablet)
			outState.putInt("sheet state", sheetBehavior.getState());
		mStepFragment.onSaveInstanceState(outState);
	}

	private void collapseBottomSheet(){
		if(!isOnTablet){
			sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
		}
	}

}
