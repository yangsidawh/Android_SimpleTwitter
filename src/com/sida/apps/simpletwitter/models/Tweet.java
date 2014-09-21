package com.sida.apps.simpletwitter.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.format.DateUtils;

public class Tweet implements Serializable{
	
	private static final long serialVersionUID = -4198035670420954170L;
	private String body;
	private long uid;
	private String createdAt;
	private User user;
	private String ago;
	public static String last_id;
	
	public static Tweet fromJson(JSONObject jsonObj)  {
		Tweet tweet = new Tweet();
		try {
			tweet.body = jsonObj.getString("text");
			tweet.uid = jsonObj.getLong("id");
			tweet.createdAt = jsonObj.getString("created_at");
			tweet.ago = getRelativeTimeAgo(tweet.createdAt);
			tweet.user = User.fromJSON(jsonObj.getJSONObject("user"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return tweet;    
	}

	public static String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
			relativeDate = relativeDate.replace("hours","h");
			relativeDate = relativeDate.replace("hour","h");
			relativeDate = relativeDate.replace("minutes","m");
			relativeDate = relativeDate.replace("minute","m");
			relativeDate = relativeDate.replace("seconds","s");
			relativeDate = relativeDate.replace("second","s");
			relativeDate = relativeDate.replace("in","");
			relativeDate = relativeDate.replace("ago","");
			relativeDate = relativeDate.trim();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
	public static ArrayList<Tweet> fromJsonArray(JSONArray json) {
		ArrayList<Tweet> tweets =  new ArrayList<Tweet>(json.length());
		for(int i = 0;i<json.length();i++) {
			JSONObject tweetjson = null;
			try {
				tweetjson = json.getJSONObject(i);
				if (i == json.length() -1) {
					last_id = tweetjson.getString("id_str");
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			Tweet tweet = Tweet.fromJson(tweetjson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}		
		return tweets;
	}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	} 
	public String getAgo() {
		return ago;
	} 

}
