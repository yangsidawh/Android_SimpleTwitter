package com.sida.apps.simpletwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	
	private static final long serialVersionUID = -7732711640714072213L;
	private String name;
	private long uid;
	private String screenName;
	private String profilePic;
	private String description;
	private int follower;
	private int following;
	
	public static User fromJSON(JSONObject json) {
		User u = new User();
		try {
			u.name = json.getString("name");
			u.uid = json.getLong("id");
			u.screenName = json.getString("screen_name");
			u.profilePic = json.getString("profile_image_url");
			u.description = json.getString("description");
			u.follower = json.getInt("followers_count");
			u.following = json.getInt("friends_count");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return u;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfilePic() {
		return profilePic;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getFollower() {
		return follower + " Followers";
	}
	
	public String getFollowing() {
		return following + " Followings";
	}
	
	
}
