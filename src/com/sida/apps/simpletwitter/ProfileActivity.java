package com.sida.apps.simpletwitter;

import org.json.JSONObject;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sida.apps.simpletwitter.fragment.UserTimelineFragment;
import com.sida.apps.simpletwitter.models.User;

public class ProfileActivity extends FragmentActivity {
	private TwitterClient client;
	private User user;
	private String screenName;
	private UserTimelineFragment userfragment;
	private ActionBar bar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		client = TwitterApp.getRestClient();
		bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#55ACEE")));
		screenName = getIntent().getStringExtra("screenName");
		getCurrentUser(screenName);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTimelineFragment fragment = UserTimelineFragment.newInstance(screenName);
		ft.replace(R.id.flUserTimeline, fragment);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void getCurrentUser(String screenName) {
		client.getUserInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				user = User.fromJSON(json);
				setHeader(user);
				bar.setTitle(" @"+user.getScreenName());
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
			}
		}, screenName);
	}
	
	private void setHeader(User u) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTag = (TextView) findViewById(R.id.tvTag);
		TextView tvFollower = (TextView) findViewById(R.id.tvFollower);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

		tvName.setText(u.getName());
		tvTag.setText(u.getDescription());
		tvFollower.setText(u.getFollower());
		tvFollowing.setText(u.getFollowing());
		ImageLoader.getInstance().displayImage(u.getProfilePic(), ivProfileImage);
	}
}
