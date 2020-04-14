package com.example.quizme.quizMe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Stack;

public class QuestionDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "question.db";
    private static final String TABLE_NAME = "question";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTION = "question";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_CORRECTANSWER = "correctAnswer";
    private static final String COLUMN_WRONGANSWER = "wrongAnswer";
    private static final String COLUMN_ANSWER = "userAnswer";
    private static final String COLUMN_GAMENUMBER = "gameNumber";

    SQLiteDatabase db;

    private static final String TABLE_CREATE ="create table question (id integer primary key autoincrement not null," +
            "gamenumber integer not null, question text not null, category text not null, correctAnswer text not null, wrongAnswer text not null, userAnswer boolean not null)";

    private Integer[] gamenumber = {1,1,1,1,1,1,1,1,1,1};
    private String[] question = {"What is the smallest animal alive?","What is the largest animal alive?","What is the slowest animal?","A snail can sleep for how many years?","A group of lions is known as?","Which bird is a universal symbol of peace?","Which animal never sleeps?","Which animal has the highest blood pressure?","A group of hedgehogs is known as?","The fingerprints of which animal extremely resembles the humans?"};
    private String[] category = {"Animals","Animals","Animals","Animals","Animals","Animals","Animals","Animals","Animals","Animals"};
    private String[] correct = {"The Paedophryne amauensis frog","Blue Whale","Sloth","3 years","A pride","Dove","Bullfrog","Giraffe","Pickles","Koala"};
    private String[] wrong = {"Paedocypris fish.Bee Hummingbird.Pygmy Rabbit","Elephant.Giraffe.Colossal Squid","Turtle.Panda.Zebra","2 years.5 years.0,5 years","Shoal.A flock.A herd","Pigeon.Eagle.Ostrich","Slug.Wasps.Cockroach","Killer whale.Meerkat.Bat","Cete.Gang.Cloud","Panda.Orangutan.Chimpanzee"};
    private Boolean[] userAnswer = {true, false, true, false, true, false, true, false, true, false};

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
            contentValues.put(COLUMN_GAMENUMBER, gamenumber[i]);
            contentValues.put(COLUMN_QUESTION, question[i]);
            contentValues.put(COLUMN_CATEGORY, category[i]);
            contentValues.put(COLUMN_CORRECTANSWER, correct[i]);
            contentValues.put(COLUMN_WRONGANSWER, wrong[i]);
            contentValues.put(COLUMN_ANSWER, userAnswer[i]);
            db.insert(TABLE_NAME, null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public Stack getQuestions(String category) {
        System.out.println("er að sækja spurningar");
        String[] columns = { COLUMN_QUESTION, COLUMN_CORRECTANSWER, COLUMN_WRONGANSWER };
        String selection = COLUMN_CATEGORY +"=?";
        String[] selectionArgs = { category };
        System.out.println("SELECTION: " + selection + "SELECTION_ARGS: " + selectionArgs);
        db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        System.out.println("CURSOR COUNT: " + count);

        // Fetch questions from db
        System.out.println("Cursor value: " + cursor);

        // To increase performance first get the index of each column in the cursor
        final int questionIndex = cursor.getColumnIndex(COLUMN_QUESTION);
        final int correctIndex = cursor.getColumnIndex(COLUMN_CORRECTANSWER);
        final int wrongIndex = cursor.getColumnIndex(COLUMN_WRONGANSWER);

        try {
            // If moveToFirst() returns false then cursor is empty
            if (!cursor.moveToFirst()) {
                return null;
            }

            Stack<Question> questions = new Stack<Question>();
            int i = 0; // ekki notað eins og er

            do {

                // Read the values of a row in the table using the indexes acquired above
                // final long id = cursor.getLong(idIndex);
                final String question = cursor.getString(questionIndex);
                final String correct = cursor.getString(correctIndex);
                final String[] wrong = splitString(cursor.getString(wrongIndex));
                System.out.println("wrong lengt "+ wrong.length);
                questions.push(new Question(question,correct,wrong));
                i++; //ekki notað
            } while (cursor.moveToNext());
            return questions;

        } finally {
            // Close the Cursor to avoid memory leaks
            cursor.close();

            // Close the database
            db.close();
        }

    }


    private static String[] splitString(String s){

        String[] subStrings = new String[7];
        int startIndex = 0;
        int numSub = 0;
        int i = 1;

        while(numSub < subStrings.length-1 && i < s.length()){
            if (s.charAt(i)=='.') {
                subStrings[numSub] = s.substring(startIndex,i);
                System.out.println(s.substring(startIndex,i));

                i++;
                startIndex = i;
                numSub++;
            }
            i++;
        }
        subStrings[numSub] = s.substring(startIndex);
        System.out.println(s.substring(startIndex));
        return subStrings;
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }

}


