package com.example.android.bakingapp.networking;

/**
 * Erzeugt von M. Fengels am 15.05.2018.
 */
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.bakingapp.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;



/**
 * Created by glm9637 on 12.03.2018 18:00.
 */

/**
 * Loader to Fetch the Recipes
 */
public class RecipeLoader extends AsyncTaskLoader<ArrayList<Recipe>> {
	
	private ArrayList<Recipe> mData;
	private boolean hasResult = false;
	
	public RecipeLoader(@NonNull Context context) {
		super(context);
	}
	
	@Nullable
	@Override
	public ArrayList<Recipe> loadInBackground() {
		return null;
	}
	
	/**
	 * parses the JsonValue to a List of Recipes
	 * @param jsonResult the JsonData from the Website
	 * @return a complete List of Recipes
	 */
	private ArrayList<Recipe> parseData(String jsonResult){
		mData = new ArrayList<>();
		try {
			JSONArray result = new JSONArray(jsonResult);
			for(int i=0;i<result.length();i++){
				Recipe recipe = new Recipe(result.getJSONObject(i));
				mData.add(recipe);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			mData = null;
		}
		return mData;
	}
	
	
	@Override
	protected void onStartLoading() {
		if (hasResult) {
			deliverResult(mData);
		}else {
			forceLoad();
		}
	}
	
	/**
	 * Loads the Data and calls the parseData Method, to parse the JsonValue to the desired Object
	 *
	 * @return null if there is no connection, otherwise the parsed data
	 */
	@Nullable
	@Override
	protected ArrayList<Recipe> onLoadInBackground() {
		
		String result = NetworkHelper.loadData("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
		
		if (result.equals("")) {
			if (!isOnline()) {
				return null;
			}
		}
		return parseData(result);
	}
	
	@Override
	public void deliverResult(final ArrayList<Recipe> data) {
		mData = data;
		hasResult = true;
		super.deliverResult(data);
	}
	
	@Override
	protected void onReset() {
		super.onReset();
		onStopLoading();
		if (hasResult) {
			mData = null;
			hasResult = false;
		}
	}
	
	
	public boolean isOnline() {
		try {
			int timeoutMs = 1500;
			Socket sock = new Socket();
			SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);
			
			sock.connect(sockaddr, timeoutMs);
			sock.close();
			
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
}
