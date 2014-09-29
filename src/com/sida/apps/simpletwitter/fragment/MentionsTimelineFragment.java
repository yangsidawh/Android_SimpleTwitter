package com.sida.apps.simpletwitter.fragment;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sida.apps.simpletwitter.TwitterApp;
import com.sida.apps.simpletwitter.TwitterClient;
import com.sida.apps.simpletwitter.models.Tweet;

public class MentionsTimelineFragment extends TweetsListFragment {
	private TwitterClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApp.getRestClient();
		populateTimeline();
	}
	
	public void populateTimeline() {
		client.getMentionsTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				addAll(Tweet.fromJsonArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
			}
		});
	}
}
