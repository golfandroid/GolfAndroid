/**
 * SharedPreferencesHelper.java
 * 2012-4-11
 */
package com.jinfang.golf.utils;


import com.jinfang.golf.GolfApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesHelper {

	public static final String APP_SHARD = "golf_rd";
	private static SharedPreferences mSharedPreferences;
	private static Editor mEditor;

	public static SharedPreferences getSharedPreferences() {

		if (mSharedPreferences == null) {
			mSharedPreferences = GolfApplication.getInstance().getSharedPreferences(APP_SHARD, Context.MODE_PRIVATE);
		}
		return mSharedPreferences;
	}

	public static Editor getEditor() {

		if (mEditor == null) {
			mEditor = getSharedPreferences().edit();
		}
		return mEditor;
	}

}
