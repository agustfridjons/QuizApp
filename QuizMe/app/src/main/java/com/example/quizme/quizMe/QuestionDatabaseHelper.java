package com.example.quizme.quizMe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "question.db";
    private static final String TABLE_NAME = "question";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_DIFFICULTY = "difficulty";
    private static final String COLUMN_CORRECTANSWER = "correctAnswer";
    private static final String COLUMN_WRONGANSWER = "wrongAnswer";

    SQLiteDatabase db;

    private static final String TABLE_CREATE ="create table question (id integer primary key autoincrement not null," +
            "question text not null, category text not null, difficulty text not null, correctAnswer text not null, wrongAnswer text not null)";

    private String[] question = {"What is the largest animal alive?", "Er þetta spurning?", "Heiti ég Ásdís?"};
    private String[] category = {"Animals", "Animals", "Sports"};
    private String[] difficulty = {"Easy", "Easy", "Medium"};
    private String[] correct = {"Blue Whale", "Já", "Fokk ja"};
    private String[] wrong = {"Elephant", "Nei", "Kannski ekki....."};

    public QuestionDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO setja inn allar spurningarnar hérna
        db.execSQL(TABLE_CREATE);
        makeQuestions(db);
    }

    private void makeQuestions(SQLiteDatabase db) {
        // Gets the data repository in write mode
      //  SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        System.out.println("komin í makeQuestions");
        for (int i = 0; i < question.length; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_QUESTION, question[i]);
            contentValues.put(COLUMN_CATEGORY, category[i]);
            contentValues.put(COLUMN_DIFFICULTY, difficulty[i]);
            contentValues.put(COLUMN_CORRECTANSWER, correct[i]);
            contentValues.put(COLUMN_WRONGANSWER, wrong[i]);
            db.insert(TABLE_NAME, null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public String[][] getQuestions(String category, String difficulty) {
        System.out.println("er að sækja spurningar");
        String[] columns = { COLUMN_QUESTION, COLUMN_CORRECTANSWER, COLUMN_WRONGANSWER };
        String selection = COLUMN_CATEGORY +"=?" + " and " + COLUMN_DIFFICULTY + "=?";
        String[] selectionArgs = { category, difficulty };
        System.out.println("SELECTION: " + selection + "SELECTION_ARGS: " + selectionArgs);
        db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        System.out.println("CURSOR COUNT: " + count);

        // Fetch questions from db
        System.out.println("Cursor value: " + cursor);

        // TODO Make a list of questions and return

        // To increase performance first get the index of each column in the cursor
        final int questionIndex = cursor.getColumnIndex(COLUMN_QUESTION);
        final int correctIndex = cursor.getColumnIndex(COLUMN_CORRECTANSWER);
        final int wrongIndex = cursor.getColumnIndex(COLUMN_WRONGANSWER);

        try {

            // If moveToFirst() returns false then cursor is empty
            if (!cursor.moveToFirst()) {
                String[][] newString = new String[0][];
                return newString;
            }

            final String[][] questions = new String[count][];
            int i = 0;

            do {

                // Read the values of a row in the table using the indexes acquired above
              //  final long id = cursor.getLong(idIndex);
                final String question = cursor.getString(questionIndex);
                final String correct = cursor.getString(correctIndex);
                final String wrong = cursor.getString(wrongIndex);

                String[] data = { question, correct, wrong};

                questions[i] = data;

                // TODO bua til Question object ekki String[][ ---- questions.add(new Question(question, correct, wrong));
                i++;
            } while (cursor.moveToNext());
            return questions;

        } finally {
            // Close the Cursor to avoid memory leaks
            cursor.close();

            // Close the database
            db.close();
        }


    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }



}


