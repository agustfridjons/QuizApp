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

    private boolean request;

    ArrayList<NewFriendItem> mUserList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        db = new UserDatabaseHelper(this);

        mBackButton = (Button) findViewById(R.id.back);
        mSearchField = (EditText) findViewById(R.id.search_field);
        mUserListView = (RecyclerView) findViewById(R.id.list_recycler_view);

        request = getIntent().getBooleanExtra("request", false);
        System.out.println("request " + request);

        mSession = new SessionManager(AddFriendActivity.this);

        //Crate a RecyclerView list of CardView items with mUserList data
        createRecyclerViewList();

        if(request){
            mSearchField.setVisibility(View.GONE);
            editList(db.getRequests(mSession.getSession()));
        }

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
                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void editList(ArrayList<String> newList){
        ArrayList<String> ignoreList = db.getFriendList(mSession.getSession());
        ignoreList.add(mSession.getSession());
        mUserList.clear();
        for (int i = 0; i < newList.size(); i++) {
            System.out.println("list contains " + newList.get(i));
            if(!ignoreList.contains(newList.get(i))) {
                System.out.println("add");
                mUserList.add(new NewFriendItem(db.getName(newList.get(i)), newList.get(i)));
            }
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

        mAdapter.setOnItemClickListener(new NewFriendAdapter.OnItemClickListener() {
            @Override
            public void onAddClick(int position) {
                NewFriendItem item = mUserList.get(position);
                String toastMessage;
                if(request){
                    db.addFriend(item.getUsername(), mSession.getSession());
                    db.addFriend(mSession.getSession(),item.getUsername());
                    toastMessage = item.getUsersName()+" added to your friend list";
                }else{
                    db.sendRequest(item.getUsername(), mSession.getSession());
                    toastMessage = "Friend request sent to "+item.getUsersName();
                }
                mUserList.remove(position);
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(AddFriendActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
