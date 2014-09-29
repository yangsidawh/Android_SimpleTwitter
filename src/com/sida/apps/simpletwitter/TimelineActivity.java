package com.sida.apps.simpletwitter;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sida.apps.simpletwitter.fragment.HomeTimelineFragment;
import com.sida.apps.simpletwitter.fragment.MentionsTimelineFragment;
import com.sida.apps.simpletwitter.fragment.TweetsListFragment;
import com.sida.apps.simpletwitter.listeners.FragmentTabListener;
import com.sida.apps.simpletwitter.models.Tweet;
import com.sida.apps.simpletwitter.models.User;


public class TimelineActivity extends FragmentActivity {
	private TwitterClient client;
	private TweetsListFragment fragTweetsList;
	private User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		fragTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.flContainer);
		client = TwitterApp.getRestClient();
		getCurrentUser();
		
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#55ACEE")));
		setupTabs();
	}
	
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText(" Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "home",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText(" Mentions")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "mentions",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}
	
	public void getCurrentUser() {
		client.getCurrentUser(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				user = User.fromJSON(json);
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
			}
		});
	}
	
	public void onComposeClick(MenuItem v) {
		Intent i = new Intent(this, ComposeActivity.class);
    	// Pass args
    	i.putExtra("user", user);
    	// Execute Intent
    	startActivityForResult(i, 5);
	}
	
	public void onProfileClick(MenuItem v) {
		Intent i = new Intent(this, ProfileActivity.class);    
		i.putExtra("user", user);
    	startActivity(i);
	}
	
	public void onProfileClick(View v) {
		Intent i = new Intent(this, ProfileActivity.class);
		String screenName = v.getTag().toString();
		i.putExtra("screenName", screenName);
    	startActivity(i);
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if (requestCode == 5) {
    		if (resultCode == RESULT_OK) {
    			Tweet tweet = (Tweet) data.getSerializableExtra("tweet");
    			fragTweetsList.insert(tweet,0);
    		}
    	}
    }
}
