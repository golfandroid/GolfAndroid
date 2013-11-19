package com.jinfang.golf.database;

import java.io.Serializable;
import java.net.URLDecoder;

import org.json.JSONObject;

import android.content.ContentValues;

public class User implements Serializable{
	private static final long serialVersionUID = 7668585453420195735L;
	private String mUserID;
	private String mUserName;
	private String mUserPassword;
	private String mUserHeadUrl;
	private long mTimestamp;
	public String getmUserID() {
		return mUserID;
	}
	public void setmUserID(String mUserID) {
		this.mUserID = mUserID;
	}
	public String getmUserName() {
		return mUserName;
	}
	public void setmUserName(String mUserName) {
		this.mUserName = mUserName;
	}
	public String getmUserPassword() {
		return mUserPassword;
	}
	public void setmUserPassword(String mUserPassword) {
		this.mUserPassword = mUserPassword;
	}
	public String getmUserHeadUrl() {
		return mUserHeadUrl;
	}
	public void setmUserHeadUrl(String mUserHeadUrl) {
		this.mUserHeadUrl = mUserHeadUrl;
	}
	public long getmTimestamp() {
		return mTimestamp;
	}
	public void setmTimestamp(long mTimestamp) {
		this.mTimestamp = mTimestamp;
	}
	public ContentValues getConentValues() {
		ContentValues values = new ContentValues();
		values.put(UserColumns.USER_ID, getmUserID());
		values.put(UserColumns.USER_NAME, getmUserName());
		values.put(UserColumns.USER_PASSWORD, getmUserPassword());
		values.put(UserColumns.USER_HEAD_URL, getmUserHeadUrl());
		values.put(UserColumns.USER_TIMESTAMP, getmTimestamp());
		return values;
	}
	
	public static User obtainGroupFromJSONObject(JSONObject json) {
		User mUser = new User();
		if (json != null && json != JSONObject.NULL && json.length() > 0) {
			mUser.setmUserID(json.isNull("userid") ? "" : json.optString("userid"));
			mUser.setmUserName(json.isNull("username") ? "" : json.optString("username"));
			mUser.setmUserPassword(json.isNull("password") ? "" : json.optString("password"));
			mUser.setmUserHeadUrl(json.isNull("headurl") ? "" : json.optString("headurl"));
			mUser.setmTimestamp(json.isNull("timestamp") ? 0 : json.optLong("timestamp"));
		}
		return mUser;
	}
}
