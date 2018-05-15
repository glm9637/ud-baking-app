package com.example.android.bakingapp.networking;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Erzeugt von M. Fengels am 15.05.2018.
 */
public class NetworkHelper {
	
	private static final OkHttpClient mClient = new OkHttpClient();
	
	/**
	 * Loads the Json-Data from the desired endpoint with the given queryParameters
	 *
	 * @param url the Url to fetch the data to
	 * @return the returned json or a empty string if a error occured
	 */
	public static String loadData(@NonNull String url) {
		HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
		
		Request request = new Request.Builder()
				.url(urlBuilder.build())
				.build();
		
		try {
			Response response = mClient.newCall(request).execute();
			
			if (response.isSuccessful() && response.body() != null) {
				return response.body().string();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}