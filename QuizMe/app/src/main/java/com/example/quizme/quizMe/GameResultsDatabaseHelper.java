package com.example.quizme.quizMe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameResultsDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "gameResults.db";
    private static final String TABLE_NAME = "gameResults";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SCORE = "score";
    private static final String COLUMN_CATEGORY = "category";
    // TODO private static final String COLUMN_CORRECTLYANSWERED = "correctlyAnswered";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table gameResults (id integer primary key autoincrement not null," +
            "score text not null, category text not null)";

    public GameResultsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public long addGameResults(String score, String category) {
        if (score.isEmpty() || category.isEmpty() ){
            return 0;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SCORE, score);
        contentValues.put(COLUMN_CATEGORY, category);
        long result = db.insert(TABLE_NAME, null , contentValues);
        db.close();
        return result;
    }

    public void checkGameResults(String score, String category){
        String[] columns = { COLUMN_ID };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_SCORE + "=?" + COLUMN_CATEGORY + "=?";
        String[] selectionArgs = { score, category };
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }
}
