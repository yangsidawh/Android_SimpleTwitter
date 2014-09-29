package com.sida.apps.simpletwitter.fragment;


import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sida.apps.simpletwitter.EndlessScrollListener;
import com.sida.apps.simpletwitter.R;
import com.sida.apps.simpletwitter.TweetArrayAdapter;
import com.sida.apps.simpletwitter.TwitterApp;
import com.sida.apps.simpletwitter.TwitterClient;
import com.sida.apps.simpletwitter.models.Tweet;

public class TweetsListFragment extends Fragment {
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private TwitterClient client;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>(); 
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		client = TwitterApp.getRestClient();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweet_list, container, false);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);		
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		        customLoadMoreDataFromApi(page); 
	                // or customLoadMoreDataFromApi(totalItemsCount); 
		    }
		});		
		return v;
	}
	
	private void customLoadMoreDataFromApi(int page) {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJsonArray(json));
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
			}
		}, Tweet.last_id);
	}
	
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}
	
	public void insert(Tweet tweet, int postion) {
		aTweets.insert(tweet, 0);
	}
}
