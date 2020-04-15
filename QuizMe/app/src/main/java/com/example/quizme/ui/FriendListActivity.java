package com.example.quizme.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizme.R;
import com.example.quizme.quizMe.SessionManager;
import com.example.quizme.quizMe.UserDatabaseHelper;
import com.example.quizme.ui.Adapters.FriendListAdapter;
import com.example.quizme.ui.items.FriendListItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    UserDatabaseHelper db;

    private TextView mLoginMessage;
    private Button mAddFriendButton ,mFriendRequestsButton;
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
        mFriendRequestsButton = (Button) findViewById(R.id.button_requests);
        mFriendListView = (RecyclerView) findViewById(R.id.list_recycler_view);
        mLoginMessage = (TextView) findViewById(R.id.login_message);

        mSession = new SessionManager(FriendListActivity.this);

        //event to go to AddFriend Activity
        View.OnClickListener addSwitchEvent = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addFriendIntent = new Intent(FriendListActivity.this, AddFriendActivity.class);
                addFriendIntent.putExtra("requests", false);
                startActivity(addFriendIntent);
            }
        };

        mFriendRequestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendRequestIntent = new Intent(FriendListActivity.this, AddFriendActivity.class);
                friendRequestIntent.putExtra("request", true);
                startActivity(friendRequestIntent);
            }
        });

        //Is session isn't empty then show friend list
        if(mSession.getSession()!=null) {
            System.out.println(mSession.getSession());
            mAddFriendButton.setVisibility(View.VISIBLE);
            mFriendRequestsButton.setVisibility(View.VISIBLE);

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


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(FriendListActivity.this, MainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.nav_friends:
                        Intent b = new Intent(FriendListActivity.this, FriendListActivity.class);
                        startActivity(b);
                        break;
                    case R.id.nav_results:
                        Intent c = new Intent(FriendListActivity.this, GameResultsActivity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });

        mAddFriendButton.setOnClickListener(addSwitchEvent);

    }

    private boolean hasChallenged(String challengerUsername) {
        ArrayList<String> challenges = db.getChallenges(mSession.getSession());
        return challenges.contains(challengerUsername);
    }

    private void deleteFriend(int position) {
        //db.deleteFriend(mSession.getSession(), mFriendList.get(position).getFriendName()))
        mFriendList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void acceptChallenge(int position) {
        //db.deleteChallenge(mSession.getSession(), mFriendList.get(position).getFriendName()))
        //TODO útfæra
        String challengerName = mFriendList.get(position).getFriendName();
        String gameID = getChallengeGameID(challengerName);
        Intent intent = new Intent(FriendListActivity.this, CategoryActivity.class);
        intent.putExtra("challengerName", challengerName); //bæta við meira
        intent.putExtra("gameID", gameID);
        startActivity(intent);
    }

    private String getChallengeGameID(String challengerName) {
        ArrayList<String> challengeList = db.getChallenges(mSession.getSession());
        String gameID="";
        for (int i = 0; i < challengeList.size(); i++) {
            if(challengeList.get(i).equals(challengerName)){
                gameID = challengeList.get(i+1);
            }
            i++;
        }
        return gameID;
    }


    private void startChallenge(int position){
        Intent intent = new Intent(FriendListActivity.this, CategoryActivity.class);
        String friendsName= mFriendList.get(position).getFriendName();
        intent.putExtra("challengerName",friendsName);
        startActivity(intent);
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
                challengeConfirmationDialog(position);
            }

            @Override
            public void onDeleteClick(int position) {
                confirmationDialog(position);
            }

            @Override
            public void onChallengeClick(int position) {
                //db.deleteChallenge(mSession.getSession(), mFriendList.get(position).getFriendName()))
                startChallenge(position);
            }
        });
    }

    private void confirmationDialog(int position) {
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
                acceptChallenge(position);
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
