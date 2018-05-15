package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.bakingapp.model.Recipe;

public class RecipeDetail extends AppCompatActivity {
	
	Recipe mRecipe;
	TextView mStepperPage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe_detail);
		mRecipe = getIntent().getParcelableExtra("Recipe");
		mStepperPage = findViewById(R.id.txt_stepper);
		mStepperPage.setText(String.format("0/%d", mRecipe.getSteps().size() + 1));
	}
}
