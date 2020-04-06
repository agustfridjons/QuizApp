package com.example.quizme.ui.items;

// This class is needed for the RecyclerView list
// TODO mögilega bæta við?
public class FriendListItem {
    private String mFriendName;

    public FriendListItem(String FriendName) {
        mFriendName = FriendName;
    }

    public String getFriendName() {
        return mFriendName;
    }

    public void setFriendName(String name) {
        mFriendName = name;
    }

}
