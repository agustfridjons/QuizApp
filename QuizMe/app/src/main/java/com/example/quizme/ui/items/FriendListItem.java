package com.example.quizme.ui.items;

// This class is needed for the RecyclerView list
// TODO mögilega bæta við?
public class FriendListItem {
    private String mFriendName;
    private String mUsername;
    private boolean mChallenge;

    public FriendListItem(String friendName, String username , boolean challenge) {
        mFriendName = friendName;
        mChallenge = challenge;
        mUsername = username;
    }

    public String getUsername() {
        return mUsername;
    }


    public String getFriendName() {
        return mFriendName;
    }

    public void setFriendName(String name) {
        mFriendName = name;
    }

    public boolean isChallenge() {
        return mChallenge;
    }

    public void setChallenge(boolean mChallenge) {
        this.mChallenge = mChallenge;
    }
}
