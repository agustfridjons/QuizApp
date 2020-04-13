package com.example.quizme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizme.R;
import com.example.quizme.quizMe.SessionManager;
import com.example.quizme.quizMe.UserDatabaseHelper;
import com.example.quizme.ui.Adapters.FriendListAdapter;
import com.example.quizme.ui.items.FriendListItem;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    UserDatabaseHelper db;

    private TextView mLoginMessage;
    private Button mAddFriendButton;
    private RecyclerView mFriendListView;
    private FriendListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SessionManager mSession;

    private ArrayList<FriendListItem> mFriendList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        db = new UserDatabaseHelper(this);

        mAddFriendButton = (Button) findViewById(R.id.new_friend_button);
        mFriendListView = (RecyclerView) findViewById(R.id.list_recycler_view);
        mLoginMessage = (TextView) findViewById(R.id.login_message);

        mSession = new SessionManager(FriendListActivity.this);

        //event to go to AddFriend Activity
        View.OnClickListener addSwitchEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFriendIntent = new Intent(FriendListActivity.this, AddFriendActivity.class);
                startActivity(addFriendIntent);
            }
        };

        //Is session isn't empty then show friend list
        if(mSession.getSession()!=null) {
            System.out.println(mSession.getSession());
            mAddFriendButton.setVisibility(View.VISIBLE);

            //Placeholder data to display list
            ArrayList<String> list = db.getFriendList(mSession.getSession());

            if (list.isEmpty()) {
                mLoginMessage.setText("You have no friends, add some");
                mLoginMessage.setOnClickListener(addSwitchEvent);
            } else {
                for (int i = 0; i < list.size(); i++) {
                    mFriendList.add(new FriendListItem(list.get(i), hasChallenged(list.get(i))));
                }
                mLoginMessage.setVisibility(View.GONE);
                mFriendListView.setVisibility(View.VISIBLE);

                //Crate a RecyclerView list of CardView items with mFreindList data
                createRecyclerViewList();
            }
        }


        mAddFriendButton.setOnClickListener(addSwitchEvent);

    }

    private boolean hasChallenged(String challengerUsername) {
        ArrayList<String> challenges = db.getChallenges(mSession.getSession());
        return challenges.contains(challengerUsername);
    }

    private void deleteFriend(int position){
        mFriendList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void startChallenge(int position){
        Intent easyIntent = new Intent(FriendListActivity.this, GameActivity.class);
        String friendsName= mFriendList.get(position).getFriendName();
        easyIntent.putExtra("challengerName",friendsName);
        String[] gameInfo;//TODO
    }

    private void createRecyclerViewList() {
        mFriendListView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new FriendListAdapter(mFriendList);

        mFriendListView.setLayoutManager(mLayoutManager);
        mFriendListView.setAdapter(mAdapter);

        //TODO klára
        mAdapter.setOnItemClickListener(new FriendListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FriendListItem item = mFriendList.get(position);
                item.setFriendName(item.getFriendName() + "Y");
                //challengeConfirmationDialog(position);
                mAdapter.notifyItemChanged(position);
            }

            @Override
            public void onDeleteClick(int position) {
                confirmationDialog(position);
            }

            @Override
            public void onChallengeClick(int position) {
                FriendListItem item = mFriendList.get(position);
                item.setFriendName(item.getFriendName() + "X");
                mAdapter.notifyItemChanged(position);
            }
        });
    }

    private void confirmationDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(FriendListActivity.this);
        builder.setTitle("Are you sure you want to delete \""+ mFriendList.get(position).getFriendName() +"\" from your friends list?").setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Delete name form friend list
                deleteFriend(position);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void challengeConfirmationDialog(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(FriendListActivity.this);
        builder.setTitle("Will you accept \""+ mFriendList.get(position).getFriendName() +"\" challenge?").setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Delete name form friend list
                startChallenge(position);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
