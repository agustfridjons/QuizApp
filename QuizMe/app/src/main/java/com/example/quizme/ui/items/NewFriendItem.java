package com.example.quizme.ui.items;

// This class is needed for the RecyclerView list
public class NewFriendItem {


        private String mUsersName;

        public NewFriendItem(String usersName) {
            mUsersName = usersName;
        }

        public String getUsersName() {
            return mUsersName;
        }

        public void setUsersName(String name) {
            mUsersName = name;
        }
}
