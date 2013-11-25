package de.messinger.dome.iHazNews;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FeedData {

	private final DbHelper dbHelper;
	private static final String TAG = "FeedData";

	static final String DATABASE = "feedreader";
	static final String TABLE = "feeds";
	static final String ID = "_id";
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String DATE = "date";
	static final String LINK = "link";
	static final String CHANNEL = "channel";
	static final int VERSION = 1;

	public FeedData(Context context) {
		this.dbHelper = new DbHelper(context);
	}

	class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table " + TABLE + " (" + ID + " int, " + TITLE
					+ " text primary key, " + DESCRIPTION + " text, " + LINK
					+ " text, " + CHANNEL + " text " + ")");
			// DATE + " date "
			Log.i(TAG, "Create Table " + TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("drop table " + TABLE);
			Log.i(TAG, "Drop Table " + TABLE);
		}
	}

	public void close() {
		dbHelper.close();
	}

	public void insert(ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Log.i(TAG, "insert");
		try {
			db.insertWithOnConflict(TABLE, null, values,
					SQLiteDatabase.CONFLICT_ABORT);
		} finally {
			db.close();
		}
	}
}
