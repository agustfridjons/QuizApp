package com.example.quizme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizme.R;
import com.example.quizme.quizMe.SessionManager;
import com.example.quizme.quizMe.UserDatabaseHelper;
import com.example.quizme.ui.Adapters.NewFriendAdapter;
import com.example.quizme.ui.items.NewFriendItem;

import java.util.ArrayList;

public class AddFriendActivity extends AppCompatActivity {

    UserDatabaseHelper db;

    private EditText mSearchField;
    private Button mBackButton;
    private RecyclerView mUserListView;
    private NewFriendAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SessionManager mSession;

    ArrayList<NewFriendItem> mUserList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        db = new UserDatabaseHelper(this);

        mBackButton = (Button) findViewById(R.id.back);
        mSearchField = (EditText) findViewById(R.id.search_field);
        mUserListView = (RecyclerView) findViewById(R.id.list_recycler_view);

        mSession = new SessionManager(AddFriendActivity.this);

        //Crate a RecyclerView list of CardView items with mUserList data
        createRecyclerViewList();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendListIntent = new Intent(AddFriendActivity.this, FriendListActivity.class);
                startActivity(friendListIntent);
            }
        });

        mSearchField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String searchKey = mSearchField.getText().toString().trim();
                    if(db.searchUsers(searchKey)!=null) {
                        editList(db.searchUsers(searchKey));
                        mAdapter.notifyDataSetChanged();
                        return true;
                    }
                }

                return false;
            }
        });

    }

    private void editList(ArrayList<String> newList){
        mUserList.clear();
        for (int i = 0; i < newList.size(); i++) {
            mUserList.add(new NewFriendItem(newList.get(i)));
        }
        mAdapter.notifyDataSetChanged();
        mUserListView.setVisibility(View.VISIBLE);
    }

    private void createRecyclerViewList() {
        mUserListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new NewFriendAdapter(mUserList);

        mUserListView.setLayoutManager(mLayoutManager);
        mUserListView.setAdapter(mAdapter);

        //TODO klára
        mAdapter.setOnItemClickListener(new NewFriendAdapter.OnItemClickListener() {
            @Override
            public void onAddClick(int position) {
                NewFriendItem item = mUserList.get(position);
                db.addFriend(item.getUsersName(), mSession.getSession());
                mUserList.remove(position);
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(AddFriendActivity.this, item.getUsersName()+" added to friend list", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
