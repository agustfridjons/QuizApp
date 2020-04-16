package com.example.quizme.ui.items;

// This class is needed for the RecyclerView list
public class NewFriendItem {


        private String mUsersName;
        private String mUsername;

        public NewFriendItem(String usersName, String username) {
            mUsersName = usersName;
            mUsername = username;
        }

        public String getUsersName() {
            return mUsersName;
        }

        public String getUsername() {
            return mUsername;
        }

        public void setUsersName(String name) {
            mUsersName = name;
        }
}
