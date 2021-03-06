package com.example.quizme.quizMe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class GameResultsDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "gameresults.db";
    private static final String TABLE_NAME = "gameresults";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_GAMEID = "gameid";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_DIFFICULTY = "difficulty";
    private static final String COLUMN_CORRECTANSWER = "correctAnswer";
    private static final String COLUMN_ANSWER = "userAnswer";
    private static final String COLUMN_GAMENUMBER = "gameNumber";
    private static final String COLUMN_USER = "userName";
    private static final String COLUMN_CHALLENGER = "challengerName";


    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table gameresults (id integer primary key autoincrement not null," +
            "gameid text not null, question text not null, category text not null, difficulty text not null, correctAnswer text not null, userAnswer boolean not null, userName text not null, challengerName text)";

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

    Integer counter = 0;

    public long addGameResults(String question, String correctAnswer, String category, String difficulty,
                               Boolean useranswer, String username, String challenger, String uniqueID) {

        if (question.isEmpty() || category.isEmpty() || correctAnswer.isEmpty() || username.isEmpty()) {
            return 0;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (counter < 7) {
            contentValues.put(COLUMN_GAMEID, uniqueID);
        }
        contentValues.put(COLUMN_QUESTION, question);
        contentValues.put(COLUMN_CATEGORY, category);
        contentValues.put(COLUMN_DIFFICULTY, difficulty);
        contentValues.put(COLUMN_CORRECTANSWER, correctAnswer);
        contentValues.put(COLUMN_ANSWER, useranswer);
        contentValues.put(COLUMN_USER, username);
        if(challenger!=null) {
            contentValues.put(COLUMN_CHALLENGER, challenger);

        }
        long result = db.insert(TABLE_NAME, null , contentValues);
        db.close();
        counter++;
        return result;
    }


    public ArrayList<String> getGameId(String username){
        String[] column = {COLUMN_GAMEID};
        String selection = COLUMN_USER + "=?";
        String selectionQuery = "SELECT * FROM " + TABLE_NAME;
        String[] selectionArgs = {username};
        db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, column, selection, selectionArgs, COLUMN_GAMEID, null, null);
        final int gameidIndex = cursor.getColumnIndex(COLUMN_GAMEID);
        try {
            if (!cursor.moveToFirst()) {
                return null;
            }
            ArrayList<String> gameId = new ArrayList<>();;
            int i = 0;
            do {
                final String gameid = cursor.getString(gameidIndex);

                gameId.add(gameid);
                i++;
            } while (cursor.moveToNext());
            return gameId;

        } finally {
            cursor.close();
        }

    }


    public ArrayList<GameResults> getGameResults(String gameid) {

        String[] columns = {COLUMN_CORRECTANSWER, COLUMN_CATEGORY, COLUMN_DIFFICULTY, COLUMN_ANSWER, COLUMN_GAMEID};

        String selection = COLUMN_GAMEID + "=?";
        String[] selectionArgs = {gameid};
        String selectionQuery = "SELECT * FROM " + TABLE_NAME;
        System.out.println("SELECTION: " + selection + "SELECTION_ARGS: " + selectionArgs);
        db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        //Cursor cursor = db.rawQuery(selectionQuery, null);
        //Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        final int correctIndex = cursor.getColumnIndex(COLUMN_CORRECTANSWER);
        final int categoryIndex = cursor.getColumnIndex(COLUMN_CATEGORY);
        final int difficultyIndex = cursor.getColumnIndex(COLUMN_DIFFICULTY);
        final int answerIndex = cursor.getColumnIndex(COLUMN_ANSWER);
        final int gameidIndex = cursor.getColumnIndex(COLUMN_GAMEID);



        try {
            if (!cursor.moveToFirst()) {
                return null;
            }
            ArrayList<GameResults> gameresults = new ArrayList<>();
            int i = 0;
            do {
                final String correct = cursor.getString(correctIndex);
                final String category = cursor.getString(categoryIndex);
                final String difficulty = cursor.getString(difficultyIndex);
                final Integer answer = cursor.getInt(answerIndex);
                final String gameID = cursor.getString(gameidIndex);

                gameresults.add(new GameResults(category, difficulty, answer, correct, gameID));

                i++;
            } while (cursor.moveToNext());
            return gameresults;

        } finally {
            cursor.close();
        }
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }
}
