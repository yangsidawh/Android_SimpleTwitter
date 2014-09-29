package com.sida.apps.simpletwitter.fragment;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sida.apps.simpletwitter.TwitterApp;
import com.sida.apps.simpletwitter.TwitterClient;
import com.sida.apps.simpletwitter.models.Tweet;

public class UserTimelineFragment extends TweetsListFragment {
	private TwitterClient client;
	private String userName;
	
	public static UserTimelineFragment newInstance(String screenName) {
		UserTimelineFragment fragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);
        fragment.setArguments(args);
        return fragment;
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApp.getRestClient();
		userName = getArguments().getString("screenName", "");	
		populateTimeline(userName);
	}
	
	public void populateTimeline(String screenName) {
		client.getUserTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJsonArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
			}
		}, screenName);
	}
	
	public void setUser(String screenName) {
		userName = screenName;
	}
}
