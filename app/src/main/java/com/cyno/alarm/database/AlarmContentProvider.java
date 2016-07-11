package com.cyno.alarm.database;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;


public class AlarmContentProvider extends ContentProvider{

	public static final String AUTHORITY = "com.cyno.alarm";
	private static final String DATABASE_NAME = "alarms_db";
	private static final int DATABASE_VERSION = 2;


	private TasksDbHelper mDatabase;

	private static final int ALL_ALARMS = 1;
	private static final int SINGLE_ALARM = 2;

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/alarms";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/alarm";

	private static final UriMatcher mURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

	static
	{
		mURIMatcher.addURI(AUTHORITY, AlarmTable.ALARM_TABLE, ALL_ALARMS);
		mURIMatcher.addURI(AUTHORITY, AlarmTable.ALARM_TABLE +"/#", SINGLE_ALARM);
	}



	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		SQLiteDatabase db = mDatabase.getWritableDatabase();
		int count = db.delete(uri.getLastPathSegment(), selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri , null , false);
		Log.d("delete" , "count = " + count);
		return count ;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		SQLiteDatabase db = mDatabase.getWritableDatabase();
		Uri mUri = ContentUris.withAppendedId(uri, db.insert(uri.getLastPathSegment(), null, values));
		getContext().getContentResolver().notifyChange(uri , null , false);
		return mUri;
	}

	@Override
	public boolean onCreate() {
		mDatabase = new TasksDbHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db;
		db = mDatabase.getReadableDatabase();
		Cursor cursor = db.query(uri.getLastPathSegment(), projection, selection, selectionArgs, null, null, sortOrder);;
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Nullable
	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
					  String[] selectionArgs) {
		SQLiteDatabase db = mDatabase.getWritableDatabase();
		int count = db.update(uri.getLastPathSegment(), values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri , null , false);
		return count;
	}

	public int bulkInsert(Uri uri, ContentValues[] values){
		int numInserted = 0;
		SQLiteDatabase sqlDB = mDatabase.getWritableDatabase();
		sqlDB.beginTransaction();
		try {
			for (ContentValues cv : values) {
				long newID = sqlDB.insertOrThrow(uri.getLastPathSegment(), null, cv);
			}
			sqlDB.setTransactionSuccessful();
			getContext().getContentResolver().notifyChange(uri, null);
			numInserted = values.length;
		} finally {
			sqlDB.endTransaction();
		}
		return numInserted;
	}


	private static class TasksDbHelper extends SQLiteOpenHelper{

		public TasksDbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			AlarmTable.onCreate(db);
			PicCodesTable.onCreate(db);
			SummaryCodesTable.onCreate(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			AlarmTable.onUpdate(db, oldVersion, newVersion);
			PicCodesTable.onUpdate(db, oldVersion, newVersion);
			SummaryCodesTable.onUpdate(db, oldVersion, newVersion);
		}
	}
}
