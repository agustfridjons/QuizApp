package com.example.quizme.quizMe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_FRIENDS = "friends";
    private static final String COLUMN_CHALLENGES = "challenges";
    private static final String COLUMN_REQUESTS = "requests";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table user (id integer primary key autoincrement not null," +
            "username text not null, password text not null, name text not null, friends text not null, challenges text not null, requests text not null)";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
        System.out.println("USEEEER ONCREATE USER EG ER I ONCREATE BITCH");
        System.out.println("BIIIIIIIITCH");
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

        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, encryptString(password));
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_FRIENDS,"");
        contentValues.put(COLUMN_CHALLENGES,"");
        contentValues.put(COLUMN_REQUESTS,"");
        long result = db.insert(TABLE_NAME, null , contentValues);
        db.close();
        return result;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = { COLUMN_ID };
        db = getReadableDatabase();
        String selection = COLUMN_USERNAME + "=?" + " and " + COLUMN_PASSWORD + "=?";

        // Encrypting password to find the right hashed password
        password = encryptString(password);

        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }

    public void sendRequest(String friendUsername, String sessionUsername) {
        String[] columns = {COLUMN_REQUESTS};
        db = getReadableDatabase();
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {friendUsername};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        ContentValues contentValues = new ContentValues();
        if (!cursor.moveToFirst()) {
            contentValues.put(COLUMN_REQUESTS,friendUsername+".");
        }else{
            contentValues.put(COLUMN_REQUESTS, cursor.getString(cursor.getColumnIndex(COLUMN_REQUESTS))+friendUsername+".");
        }

        db = getWritableDatabase();
        db.update(TABLE_NAME, contentValues,COLUMN_USERNAME+"=?",selectionArgs );
        cursor.close();
        db.close();
    }

    public ArrayList<String> getRequests(String sessionUsername){
        String[] columns = {COLUMN_REQUESTS};
        db = getReadableDatabase();
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {sessionUsername};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (!cursor.moveToFirst()) {
            System.out.println("cursor tómur");
            return null;
        }

        String friends = cursor.getString(cursor.getColumnIndex(COLUMN_REQUESTS));
        cursor.close();
        db.close();

        return splitString(friends);
    }



    public void addFriend(String friendUsername, String sessionUsername) {
        String[] columns = {COLUMN_FRIENDS};
        db = getReadableDatabase();
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {sessionUsername};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        ContentValues contentValues = new ContentValues();
        if (!cursor.moveToFirst()) {
            System.out.println("Cusror tómur TÍK");
            contentValues.put(COLUMN_FRIENDS,friendUsername+".");
        }else{
            contentValues.put(COLUMN_FRIENDS, cursor.getString(cursor.getColumnIndex(COLUMN_FRIENDS))+friendUsername+".");
        }

        db = getWritableDatabase();
        db.update(TABLE_NAME, contentValues,COLUMN_USERNAME+"=?",selectionArgs );
        cursor.close();
        db.close();
    }

    public ArrayList<String> getFriendList(String username){
        String[] columns = {COLUMN_FRIENDS};
        db = getReadableDatabase();
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (!cursor.moveToFirst()) {
            System.out.println("cursor tómur");
            return null;
        }

        String friends = cursor.getString(cursor.getColumnIndex(COLUMN_FRIENDS));
        cursor.close();
        db.close();

        return splitString(friends);
    }

    public void addChallenger(String friendUsername, String sessionUsername, String gameID) {
        String[] columns = {COLUMN_CHALLENGES};
        db = getReadableDatabase();
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {sessionUsername};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        ContentValues contentValues = new ContentValues();
        if (!cursor.moveToFirst()) {
            System.out.println("Cusror tómur TÍK");
            contentValues.put(COLUMN_CHALLENGES,friendUsername+"."+gameID+".");
        }else{
            contentValues.put(COLUMN_CHALLENGES, cursor.getString(cursor.getColumnIndex(COLUMN_CHALLENGES))+friendUsername+"."+gameID+".");
        }

        db = getWritableDatabase();
        db.update(TABLE_NAME, contentValues,COLUMN_USERNAME+"=?",selectionArgs );
        cursor.close();
        db.close();
    }

    public ArrayList<String> getChallenges(String username) {
        String[] columns = {COLUMN_CHALLENGES};
        db = getReadableDatabase();
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (!cursor.moveToFirst()) {
            System.out.println("Cusror tómur TÍK");
            return null;
        }
        String challenges = cursor.getString(cursor.getColumnIndex(COLUMN_CHALLENGES));

        cursor.close();
        db.close();

        return splitString(challenges);
    }

    public ArrayList<String> searchUsers(String key){
        String[] columns = {COLUMN_NAME};
        db = getReadableDatabase();
        String selection = COLUMN_NAME + " like ?";
        String[] selectionArgs = {"%"+key+"%"};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        System.out.println("CURSOR COUNT: " + cursor.getCount());

        // Fetch questions from db
        System.out.println("Cursor value: " + cursor);

        int nameIndex = cursor.getColumnIndex(COLUMN_NAME);

        ArrayList<String> names = new ArrayList<>();
        try {

            // If moveToFirst() returns false then cursor is empty
            if (!cursor.moveToFirst()) {
                System.out.println("cursor tómur");
                return null;
            }

            do {
                // Read the values of a row in the table using the indexes acquired above
                final String name = cursor.getString(nameIndex);
                names.add(name);
            } while (cursor.moveToNext());
            return names;

        } finally {
            // Close the Cursor to avoid memory leaks
            cursor.close();

            // Close the database
            db.close();
        }

    }

    private static ArrayList<String> splitString(String s){

        ArrayList<String> subStrings = new ArrayList<>();

        if(s.isEmpty())
            return subStrings;

        int startIndex = 0;

        for (int i = 2; i < s.length(); i++) {
            if (s.charAt(i)=='.') {
                subStrings.add(s.substring(startIndex,i));
                System.out.println(s.substring(startIndex,i));
                i++;
                startIndex = i;
            }
        }
        return subStrings;
    }

    public String encryptString(String s){
        try {
            s = encryptMD5(s.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        return s;
    }

    //Hashig passords with MD5 library
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
