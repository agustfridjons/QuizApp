package com.example.quizme.quizMe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuestionDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "question.db";
    private static final String TABLE_NAME = "question";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTIONS = "questions";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_CORRECTANSWER = "correctAnswer";
    private static final String COLUMN_WRONGANSWER = "wrongAnswer";
    private static final String COLUMN_DIFFICULTY = "difficulty";

    SQLiteDatabase db;

    private static final String TABLE_CREATE ="create table question (id integer primary key autoincrement not null," +
            "questions text not null, category text not null, correctAnswer text not null, wrongAnswer text not null)";

    public QuestionDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO setja inn allar spurningarnar hérna
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void getQuestion(String category, String difficulty) {
        String[] columns = { COLUMN_ID };
        String selection = COLUMN_CATEGORY +"=?" + " and " + COLUMN_DIFFICULTY + "=?";
        String[] selectionArgs = { category, difficulty };
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }
}


