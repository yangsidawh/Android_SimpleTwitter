package com.sida.apps.simpletwitter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sida.apps.simpletwitter.models.Tweet;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	Typeface typeLight;
	Typeface typeThin;
	Intent i;

	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
		typeLight = Typeface.createFromAsset(context.getAssets(),"font/Roboto-Light.ttf"); 
		typeThin = Typeface.createFromAsset(context.getAssets(),"font/Roboto-Thin.ttf"); 
		i = new Intent(context, ProfileActivity.class);    
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		View v; 
		if(convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		
		ImageView ivProfile = (ImageView) v.findViewById(R.id.ivProfile);
		TextView tvUsername= (TextView) v.findViewById(R.id.tvUserName);
		TextView tvScreenname= (TextView) v.findViewById(R.id.tvScreenName);
		TextView tvTime = (TextView) v.findViewById(R.id.tvTime);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		
		ivProfile.setImageResource(android.R.color.transparent);
		ivProfile.setTag(tweet.getUser().getScreenName());
		ImageLoader loader = ImageLoader.getInstance();
		loader.displayImage(tweet.getUser().getProfilePic(), ivProfile);
		tvUsername.setText(tweet.getUser().getName());
		tvBody.setText(tweet.getBody());
		tvTime.setText(tweet.getAgo());
		tvTime.setTypeface(typeThin);
		tvScreenname.setText("@" + tweet.getUser().getScreenName());
		tvScreenname.setTypeface(typeLight);
		return v;

	}
	
	
	
}
