package com.jinfang.golf.database;

import java.util.ArrayList;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class UserTable {
	
	private synchronized static Cursor getQueryGroupCursor(Context context,
			String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		return context.getContentResolver().query(AppProvider.GOLF_USER_CONTENT_URI,
				projection, selection, selectionArgs, sortOrder);
	}

	private synchronized static int updategroup(Context context, ContentValues values,
			String where, String[] selectionArgs) {
		return context.getContentResolver().update(AppProvider.GOLF_USER_CONTENT_URI,
				values, where, selectionArgs);
	}

	private synchronized static int deletegroup(Context context, String where,
			String[] selectionArgs) {
		return context.getContentResolver().delete(AppProvider.GOLF_USER_CONTENT_URI,
				where, selectionArgs);
	}

	private synchronized static long insertgroup(Context context, ContentValues values) {
		Uri uri = context.getContentResolver().insert(AppProvider.GOLF_USER_CONTENT_URI,
				values);
		return ContentUris.parseId(uri);
	}
	public synchronized static User getUserByID(Context context, String mUserID) {
		
		String selection = UserColumns.USER_ID + " = '" + mUserID + "'";
		Cursor cursor = getQueryGroupCursor(context, null, selection, null,
				null);
		User mUser = null;
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				mUser = new User();
				String mUserName = cursor.getString(cursor
						.getColumnIndex(UserColumns.USER_NAME));
				String mUserPassword = cursor.getString(cursor
						.getColumnIndex(UserColumns.USER_PASSWORD));
				String mUserHeadUrl = cursor.getString(cursor
						.getColumnIndex(UserColumns.USER_HEAD_URL));
				long timestamp = cursor.getLong(cursor
						.getColumnIndex(UserColumns.USER_TIMESTAMP));
				mUser.setmUserID(mUserID);
				mUser.setmUserName(mUserName);
				mUser.setmUserPassword(mUserPassword);
				mUser.setmUserHeadUrl(mUserHeadUrl);
				mUser.setmTimestamp(timestamp);
	
			}
			cursor.close();
		}
		return mUser;
	}
	public synchronized static ArrayList<User> getAllGroup(Context context) {
		String order = UserColumns.USER_ID + " desc";
		Cursor cursor = getQueryGroupCursor(context, null, null, null, order);
		ArrayList<User> mUserList = null;
		if (cursor != null) {
			mUserList = new ArrayList<User>();
			User mUser;
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				mUser = new User();
				String mUserID = cursor.getString(cursor
						.getColumnIndex(UserColumns.USER_ID));
				String mUserName = cursor.getString(cursor
						.getColumnIndex(UserColumns.USER_NAME));
				String mUserPassword = cursor.getString(cursor
						.getColumnIndex(UserColumns.USER_PASSWORD));
				String mUserHeadUrl = cursor.getString(cursor
						.getColumnIndex(UserColumns.USER_HEAD_URL));
				long timestamp = cursor.getLong(cursor
						.getColumnIndex(UserColumns.USER_TIMESTAMP));
				mUser.setmUserID(mUserID);
				mUser.setmUserName(mUserName);
				mUser.setmUserPassword(mUserPassword);
				mUser.setmUserHeadUrl(mUserHeadUrl);
				mUser.setmTimestamp(timestamp);
				mUserList.add(mUser);
			}
			if(!cursor.isClosed()){
				cursor.close();
			}
		}
		return mUserList;

	}

	public synchronized static int updateGroup(Context context, User mUser) {
		String where = UserColumns.USER_ID + " = '" + mUser.getmUserID() + "'";
		ContentValues values = new ContentValues();
		values.put(UserColumns.USER_ID, mUser.getmUserHeadUrl());
		values.put(UserColumns.USER_NAME, mUser.getmUserName());
		values.put(UserColumns.USER_PASSWORD, mUser.getmUserPassword());
		values.put(UserColumns.USER_HEAD_URL, mUser.getmUserHeadUrl());
		values.put(UserColumns.USER_TIMESTAMP, mUser.getmTimestamp());
		return updategroup(context, values, where, null);
	}

	public synchronized static int deleteGroup(Context context, User mUser) {
		String userWhere = UserColumns.USER_ID + " ='" + mUser.getmUserID() + "'";
		return deletegroup(context, userWhere, null);
	}
	
	public synchronized static long insertGroup(Context context, User mUser) {
		return insertgroup(context, mUser.getConentValues());
	}
}

