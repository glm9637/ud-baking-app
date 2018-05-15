package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Erzeugt von M. Fengels am 15.05.2018.
 */
public class Recipe implements Parcelable, Serializable {
	
	private int id;
	private String name;
	private String image;
	private int servings;
	private ArrayList<Ingredient> ingredients;
	private ArrayList<Step> steps;
	
	public Recipe(JSONObject jsonRecipe) throws JSONException {
		id=jsonRecipe.getInt("id");
		name = jsonRecipe.getString("name");
		image = jsonRecipe.getString("image");
		servings = jsonRecipe.getInt("servings");
		ingredients = new ArrayList<>();
		JSONArray jsonIngredients = jsonRecipe.getJSONArray("ingredients");
		for(int i=0;i<jsonIngredients.length();i++){
			ingredients.add(new Ingredient(jsonIngredients.getJSONObject(i)));
		}
		
		steps = new ArrayList<>();
		JSONArray jsonSteps = jsonRecipe.getJSONArray("steps");
		for(int i=0;i<jsonSteps.length();i++){
			steps.add(new Step(jsonSteps.getJSONObject(i)));
		}
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	public Integer getServings() {
		return servings;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.name);
		dest.writeString(this.image);
		dest.writeInt(this.servings);
		dest.writeList(this.ingredients);
		dest.writeList(this.steps);
	}
	
	protected Recipe(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
		this.image = in.readString();
		this.servings = in.readInt();
		this.ingredients = new ArrayList<Ingredient>();
		in.readList(this.ingredients, Ingredient.class.getClassLoader());
		this.steps = new ArrayList<Step>();
		in.readList(this.steps, Step.class.getClassLoader());
	}
	
	public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
		@Override
		public Recipe createFromParcel(Parcel source) {
			return new Recipe(source);
		}
		
		@Override
		public Recipe[] newArray(int size) {
			return new Recipe[size];
		}
	};
	
	public ArrayList<Step> getSteps() {
		return steps;
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}
}
