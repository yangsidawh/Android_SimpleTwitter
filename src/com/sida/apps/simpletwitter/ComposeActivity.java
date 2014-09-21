package com.sida.apps.simpletwitter;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sida.apps.simpletwitter.models.Tweet;
import com.sida.apps.simpletwitter.models.User;

public class ComposeActivity extends Activity {
	private User user;
	private ImageView ivProfile;
	private TextView tvName;
	private TextView tvScName;
	private Typeface typeLight;
	private Typeface typeThin;
	private EditText etTweet;
	private TwitterClient client;
	private MenuItem tLength;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		user  = (User) getIntent().getSerializableExtra("user");
		ivProfile = (ImageView) findViewById(R.id.ivProfileCompose);
		tvName = (TextView) findViewById(R.id.tvNameCompose);
		tvScName = (TextView) findViewById(R.id.tvScreenNameCompose);
		etTweet = (EditText) findViewById(R.id.etTweet);
		client = TwitterApp.getRestClient();

		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#55ACEE")));

		ImageLoader loader = ImageLoader.getInstance();
		loader.displayImage(user.getProfilePic(), ivProfile);
		typeLight = Typeface.createFromAsset(getAssets(),"font/Roboto-Light.ttf"); 
		typeThin = Typeface.createFromAsset(getAssets(),"font/Roboto-Thin.ttf"); 
		etTweet.setTypeface(typeLight);
		tvName.setText(user.getName());
		tvScName.setText("@"+user.getScreenName());
		
		tvName.setTypeface(typeLight);
		tvScName.setTypeface(typeThin);		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		tLength = (MenuItem) menu.findItem(R.id.label_length);
		
		etTweet.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	            int length = 140 - etTweet.getText().toString().length();
	            tLength.setTitle(String.valueOf(length));
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
	    }); 
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}
	
	public void onTweetClick(MenuItem m) {
		String status = etTweet.getText().toString();
		if (status != null && status.trim() != "") {
			client.sendTweet(new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject json) {
					Tweet tweet = Tweet.fromJson(json);
					
					Intent i = new Intent();
					
					i.putExtra("tweet", tweet);
					setResult(RESULT_OK, i);
					finish();
				}
				
				@Override
				public void onFailure(Throwable e, String s) {
					Log.d("debug", e.toString());
				}
	
			}, status);
		}
	}
	
}
