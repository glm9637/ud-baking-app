package com.example.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Erzeugt von M. Fengels am 15.05.2018.
 */
public class Step implements Parcelable, Serializable {
	
	private int id;
	private String shortDescription;
	private String description;
	private String videoUrl;
	private String thumbnailUrl;
	
	public Step(JSONObject jsonStep) throws JSONException {
		id = jsonStep.getInt("id");
		shortDescription = jsonStep.getString("shortDescription");
		description = jsonStep.getString("description");
		videoUrl = jsonStep.getString("videoURL");
		thumbnailUrl = jsonStep.getString("thumbnailURL");
	}
	
	public int getId() {
		return id;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getVideoUrl() {
		return videoUrl;
	}
	
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.id);
		dest.writeString(this.shortDescription);
		dest.writeString(this.description);
		dest.writeString(this.videoUrl);
		dest.writeString(this.thumbnailUrl);
	}
	
	protected Step(Parcel in) {
		this.id = in.readInt();
		this.shortDescription = in.readString();
		this.description = in.readString();
		this.videoUrl = in.readString();
		this.thumbnailUrl = in.readString();
	}
	
	public static final Parcelable.Creator<Step> CREATOR = new Parcelable.Creator<Step>() {
		@Override
		public Step createFromParcel(Parcel source) {
			return new Step(source);
		}
		
		@Override
		public Step[] newArray(int size) {
			return new Step[size];
		}
	};
}
