package com.example.quizme.quizMe;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class User {
    int id;
    String username, password, name;
    List<String> friendList;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = username;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String username) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addFriend(String friendName){
        friendList.add(friendName);
    }

    public void deleteFriend(int index){
        friendList.remove(index);
    }

    // TODO gameHistory
}
