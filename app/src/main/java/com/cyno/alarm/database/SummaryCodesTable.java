package com.cyno.alarm.database;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


public class SummaryCodesTable
{
	public static final String TABLE_NAME = "Summary_codes";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AlarmContentProvider.AUTHORITY
			+ "/" + TABLE_NAME);

	public static final String COL_ID = "_id";
	public static final String COL_UNIQUE_CODE = "unique_code";
	public static final String COL_LANGUAGE_CODE = "language_code";
	public static final String COL_DAY_SUMMARY = "day_summary";
	public static final String COL_NIGHT_SUMMARY = "night_summary";

	private static final String DATABASE_CREATE_NEW = "create table "
			+ TABLE_NAME
			+ "("
			+ COL_ID + " integer primary key autoincrement, "
			+ COL_UNIQUE_CODE + " TEXT , "
			+ COL_LANGUAGE_CODE+ " TEXT , "
			+ COL_DAY_SUMMARY+ " TEXT , "
			+ COL_NIGHT_SUMMARY + " TEXT  "
			+ ");";


	public static void onCreate(SQLiteDatabase mDatabase)
	{
		mDatabase.execSQL(DATABASE_CREATE_NEW);
	}
	public static void onUpdate(SQLiteDatabase mDatabase , int oldVer, int newVer){
	}


	@Override
	public String toString() {
		return super.toString();
	}
}
