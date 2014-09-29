package com.sida.apps.simpletwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "QAoTvzJKEslbZAEZos4CeiUXi";       // Change this
	public static final String REST_CONSUMER_SECRET = "WRMrWI458bDeORe8o4N0k7hDTlkyU8BFPyQsyyASQOdGeuAfX6"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletwitter"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	
	public void getHomeTimeline(AsyncHttpResponseHandler handler, String offset) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		if (offset != null) {
			params.put("max_id", offset);
			client.get(apiUrl, params, handler);
		} else {
			client.get(apiUrl, null, handler);
		}
	}

	public void getCurrentUser(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		client.get(apiUrl, null, handler);
	}
	
	public void getUserInfo(AsyncHttpResponseHandler handler, String screenName) {
		if (screenName != null) {
			String apiUrl = getApiUrl("users/show.json");
			RequestParams params = new RequestParams();
			params.put("screen_name", screenName);
			client.get(apiUrl, params, handler);
		} else {
			getCurrentUser(handler);
		}
	}
	
	public void sendTweet(AsyncHttpResponseHandler handler, String tweet) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", tweet);
		client.post(apiUrl, params, handler);
	}

	public void getMentionsTimeline(JsonHttpResponseHandler handler) {
		// TODO Auto-generated method stub
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		client.get(apiUrl, null, handler);	
//		if (offset != null) {
//			params.put("max_id", offset);
//			client.get(apiUrl, params, handler);
//		} else {
//			client.get(apiUrl, null, handler);
//		}
	}
	
	public void getUserTimeline(JsonHttpResponseHandler handler, String screen_name) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		if (screen_name != null) {
			RequestParams params = new RequestParams();
			params.put("screen_name", screen_name);
			client.get(apiUrl, params, handler);	
		} else {
			client.get(apiUrl, null, handler);	
		}
	}
}