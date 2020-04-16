package com.example.quizme.quizMe;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_username";
    String SESSION_NAME = "session_name";

    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String username,String name){
        //save session of user whenever user is logged in

        editor.putString(SESSION_KEY,username).commit();
        editor.putString(SESSION_NAME,name).commit();
    }

    public String getSession(){
        //return user id whose session is saved
        return sharedPreferences.getString(SESSION_KEY, null);
    }

    public String getSessionName(){
        //return user id whose session is saved
        return sharedPreferences.getString(SESSION_NAME, null);
    }

    public void removeSession(){
        editor.putString(SESSION_KEY,null).commit();
        editor.putString(SESSION_NAME,null).commit();
    }
}
