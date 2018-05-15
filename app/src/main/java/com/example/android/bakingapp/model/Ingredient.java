package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Erzeugt von M. Fengels am 15.05.2018.
 */
public class Ingredient implements Parcelable {
	
	private double quantity;
	private String measure;
	private String name;
	
	public Ingredient(JSONObject jsonIngredient) throws JSONException {
		quantity = jsonIngredient.getDouble("quantity");
		measure = jsonIngredient.getString("measure");
		name = jsonIngredient.getString("ingredient");
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public String getMeasure() {
		return measure;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(this.quantity);
		dest.writeString(this.measure);
		dest.writeString(this.name);
	}
	
	protected Ingredient(Parcel in) {
		this.quantity = in.readDouble();
		this.measure = in.readString();
		this.name = in.readString();
	}
	
	public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
		@Override
		public Ingredient createFromParcel(Parcel source) {
			return new Ingredient(source);
		}
		
		@Override
		public Ingredient[] newArray(int size) {
			return new Ingredient[size];
		}
	};
}
