package com.example.quizme.quizMe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigInteger;
import java.security.MessageDigest;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_FRIENDS = "friends";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table user (id integer primary key autoincrement not null," +
            "username text not null, password text not null, name text not null, friends text)";

    public UserDatabaseHelper(Context context) {
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

    public long addUser(String username, String password, String name) {
        // TODO kannski gera þetta öðruvisi

        if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
            return 0;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, encryptString(password));
        contentValues.put(COLUMN_NAME, name);
        long result = db.insert(TABLE_NAME, null , contentValues);
        db.close();
        return result;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = { COLUMN_ID };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUMN_USERNAME + "=?" + " and " + COLUMN_PASSWORD + "=?";

        // Encrypting password to find the right hashed password
        password = encryptString(password);

        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        //if (count>0) eh að reyna að skilja cursor
            //System.out.println("column count: "+cursor.getCount()+ /*" column count: "+cursor.getColumnIndex(COLUMN_NAME) */" index "+cursor.getColumnIndex(COLUMN_USERNAME)+": "+cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))/*+" index 1: "+cursor.getString(1)+" index 2: "+cursor.getString(2)*/);
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }


    public static void addFriend(String friend){
        //eftir að útfæra þarf að vita hvernig cursor virkar
    }

    public String encryptString(String s){
        try {
            s = encryptMD5(s.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }

    //This function its fount on the web
    public static String encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);

        BigInteger md5Data = null;

        try {
            md5Data = new BigInteger(1, md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5Data.toString(16);

    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }
}
