package hu.sbenkovi.szotar_proba.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryDBAdapter {

	private final DictionaryHelper helper;

	public DictionaryDBAdapter(Context context) {
		helper = DictionaryHelper.getInstance(context);
	}

	public long insertData(String engWord, String hunWord, int difficulty, int ratio) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DictionaryHelper.ENGWORD, engWord);
		contentValues.put(DictionaryHelper.HUNWORD, hunWord);
		contentValues.put(DictionaryHelper.DIFFICULTY, difficulty);
		contentValues.put(DictionaryHelper.RATIO, ratio);
		long result = db.insert(DictionaryHelper.TABLE_NAME, null, contentValues);
		db.close();
		return result;
	}

	public Map<String, String> getAllWords() {
		Map<String, String> result = new HashMap<String, String>();
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = { DictionaryHelper.ENGWORD, DictionaryHelper.HUNWORD };
		Cursor cursor = db.query(DictionaryHelper.TABLE_NAME, columns, null, null, null, null, null);
		while(cursor.moveToNext()) {
			int engWordColIndex = cursor.getColumnIndex(DictionaryHelper.ENGWORD);
			String engWord = cursor.getString(engWordColIndex);
			int hunWordColIndex = cursor.getColumnIndex(DictionaryHelper.HUNWORD);
			String hunWord = cursor.getString(hunWordColIndex);
			if(shouldReverse()) {
				result.put(hunWord, engWord);
			} else {
				result.put(engWord, hunWord);
			}
		}
		return result;
	}

	private boolean shouldReverse() {
		Random random = new Random();
		int i = random.nextInt(99);
		return i % 3 == 0;
	}

	static class DictionaryHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "dictionary.db";
		private static final String TABLE_NAME = "WORDS";
		private static final int DATABASE_VERSION = 6;
		private static final String UID = "_id";
		private static final String ENGWORD = "EngWord";
		private static final String HUNWORD = "HunWord";
		private static final String DIFFICULTY = "Difficulty";
		private static final String RATIO = "Ratio";
		private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + UID
		        + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ENGWORD + " VARCHAR(255), " + HUNWORD + " VARCHAR(255), "
		        + DIFFICULTY + " INTEGER, " + RATIO + " INTEGER);";
		private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
		private final Context context;
		private static DictionaryHelper helperInstance;

		public static DictionaryHelper getInstance(Context context) {
			if(helperInstance == null) {
				helperInstance = new DictionaryHelper(context.getApplicationContext());
			}
			return helperInstance;
		}

		private DictionaryHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
			Message.message(context, "constructor called");
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(CREATE_TABLE);
				Message.message(context, DATABASE_NAME + " created");
			} catch(SQLException e) {
				Message.message(context, "" + e);
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			try {
				db.execSQL(DROP_TABLE);
				Message.message(context, DATABASE_NAME + "dropped");
				onCreate(db);
			} catch(SQLException e) {
				Message.message(context, "" + e);
			}
		}
	}
}
