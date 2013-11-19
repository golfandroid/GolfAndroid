
package com.jinfang.golf.database;

import java.util.HashMap;



import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class AppProvider extends ContentProvider {
	private static final int GOLF_USER = 1;
	private static final int GOLF_USER_ID = 2;
	private static HashMap<String, String> sCardsProjectionMap;
	public static final String AUTHORITY = "com.jinfang.golf.provider";
	public static final Uri GOLF_USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/golf_user");
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private static String DEFAULT_SORT_ORDER = null;
	private static DaoBase mDaoBase;
	@Override
	public boolean onCreate() {
		mDaoBase = DaoBase.getInstance(getContext());
		mDaoBase.getReadableDatabase();
		return true;
	}

	@Override
	public synchronized Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String orderBy;
		switch (sUriMatcher.match(uri)) {
		case GOLF_USER:
			qb.setTables(DaoBase.TABLE_USER);
			break;
		case GOLF_USER_ID:
			qb.setTables(DaoBase.TABLE_USER);
			qb.appendWhere(UserColumns.USER_ID);
			qb.appendWhere("=");
			qb.appendWhere(uri.getPathSegments().get(1));
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		SQLiteDatabase db = mDaoBase.getReadableDatabase();
		Cursor mCursor = null;
		try {
			mCursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
			mCursor.setNotificationUri(getContext().getContentResolver(), uri);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return mCursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public synchronized Uri insert(Uri uri, ContentValues initialValues) {
		String table;
		if (sUriMatcher.match(uri) == GOLF_USER) {
			table = DaoBase.TABLE_USER;
		}  else {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		Uri mUri = null;
		SQLiteDatabase db = mDaoBase.getWritableDatabase();
		try {
			long rowId = db.insert(table, null, values);
			if (rowId > 0) {
			    mUri = ContentUris.withAppendedId(uri, rowId);
				getContext().getContentResolver().notifyChange(mUri, null);
				
			} else {
				throw new SQLException("Failed to insert row into " + uri);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(db.isOpen()){
				db.close();
			}
		}
		return mUri;
	}

	@Override
	public synchronized int delete(Uri uri, String selection, String[] selectionArgs) {
		String table;
		if (sUriMatcher.match(uri) == GOLF_USER) {
			table = DaoBase.TABLE_USER;
		} else {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		SQLiteDatabase db = mDaoBase.getWritableDatabase();
		int count = 0;
		try {
			switch (sUriMatcher.match(uri)) {
			case GOLF_USER:
				count = db.delete(table, selection, selectionArgs);
				break;
			case GOLF_USER_ID:
				String mUserID = uri.getPathSegments().get(1);
				count = db.delete(table, UserColumns.USER_ID + "=" + mUserID + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
			}
			getContext().getContentResolver().notifyChange(uri, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(db.isOpen()){
				db.close();
			}
		}
		return count;
	}

	@Override
	public synchronized int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		String table;
		if (sUriMatcher.match(uri) == GOLF_USER) {
			table = DaoBase.TABLE_USER;
		} else {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		SQLiteDatabase db = mDaoBase.getWritableDatabase();
		int count = 0;
		try {
			switch (sUriMatcher.match(uri)) {
			case GOLF_USER:
				count = db.update(table, values, selection, selectionArgs);
				break;

			case GOLF_USER_ID:
				String mUserID = uri.getPathSegments().get(1);
				count = db.update(table, values, UserColumns.USER_ID + "=" + mUserID + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
						selectionArgs);
				break;
			
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
			}
			getContext().getContentResolver().notifyChange(uri, null);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(db.isOpen()){
				db.close();
			}
		}
		return count;
	}

	@Override
	public synchronized int bulkInsert(Uri uri, ContentValues[] values) {
		String table;
		if (sUriMatcher.match(uri) == GOLF_USER) {
			table = DaoBase.TABLE_USER;
		}  else {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		SQLiteDatabase db = mDaoBase.getWritableDatabase();
		db.beginTransaction();
		int effectRows = 0;
		try {
			for (int i = 0, size = values.length; i < size; i++) {
				ContentValues value = values[i];
				db.insert(table, null, value);
				effectRows++;
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.endTransaction();
			db.close();
		}
		return effectRows;
	}

	static {
		sUriMatcher.addURI(AUTHORITY, "golf_user", GOLF_USER);
		sUriMatcher.addURI(AUTHORITY, "golf_user/#", GOLF_USER_ID);
	}
}
