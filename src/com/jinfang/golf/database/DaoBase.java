package com.jinfang.golf.database;

import java.io.File;

import com.jinfang.golf.constants.AppConstants;
import com.jinfang.golf.utils.SharedPreferencesHelper;




import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Build;
import android.os.Environment;


public class DaoBase extends SQLiteOpenHelper {


	private final static int DATABASE_VERSION = 1;   
	private static String DATABASE_NAME = "jinfang_golf.db";
	private static DaoBase mDaoBaseInstance = null;
	public SQLiteDatabase mDatabase = null;
	public static final String TABLE_USER = "golf_user";

	static {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && Build.VERSION.SDK_INT >= 8) {
			String DATABASE_DIR = Environment.getExternalStorageDirectory() + AppConstants.ARECORD_DB_DIR;
			File dir = new File(DATABASE_DIR);
			if (dir.exists() || (!dir.exists() && dir.mkdirs())) {
				DATABASE_NAME = DATABASE_DIR + DATABASE_NAME;
			}
		}
	}

	private DaoBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public static synchronized DaoBase getInstance(Context context) {
		if (mDaoBaseInstance == null) {
			mDaoBaseInstance = new DaoBase(context);
		}
		return mDaoBaseInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTableUser(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

	private void createTableUser(SQLiteDatabase db) {
		String table_user_sql = "create table IF NOT EXISTS  " + TABLE_USER 
				+ "(" + UserColumns.USER_ID + " varchar PRIMARY KEY, "
				+ UserColumns.USER_NAME + " varchar, "
				+ UserColumns.USER_PASSWORD + " varchar, "
				+ UserColumns.USER_HEAD_URL + " varchar, "
				+ UserColumns.USER_TIMESTAMP + " long" + ");" ;
		try {
			db.execSQL(table_user_sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
	}

	public synchronized void execSQL(String sql) {
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	public synchronized  Cursor query(String table, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(table);
		SQLiteDatabase db = getReadableDatabase();
		Cursor mCursor = null;
		try {
			mCursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return mCursor;
	}

	public synchronized long insert(String table, ContentValues initialValues) {
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		SQLiteDatabase db = getWritableDatabase();
		long rowId = -1;
		try {
			rowId = db.insert(table, null, values);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return rowId;
	}

	public synchronized int delete(String table, String selection, String[] selectionArgs) {
		SQLiteDatabase db = getWritableDatabase();
		int count = -1;
		try {
			count = db.delete(table, selection, selectionArgs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return count;
	}

	public synchronized int update(String table, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = getWritableDatabase();
		int count = -1;

		try {
			count = db.update(table, values, selection, selectionArgs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		return count;
	}
}
