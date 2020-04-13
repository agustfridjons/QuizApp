package com.example.quizme.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizme.R;
import com.example.quizme.ui.Adapters.FriendListAdapter;
import com.example.quizme.ui.items.FriendListItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    private Button mBackButton;
    private RecyclerView mFriendListView;
    private FriendListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<FriendListItem> mFriendList = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        mBackButton = (Button) findViewById(R.id.back_button);
        mFriendListView = (RecyclerView) findViewById(R.id.list_recycler_view);

        //Placeholder data to display list
        //TODO breyta í réttan vina lista
        for (int i = 1; i <= 20; i++) {
            mFriendList.add(new FriendListItem("Username " + i));
        }

        //Crate a RecyclerView list of CardView items with mFreindList data
        createRecyclerViewList();

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
    }

    private void deleteFriend(int position) {
        mFriendList.remove(position);
        mAdapter.notifyItemRemoved(position);
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
            public void onItemClick(int position, boolean delete) {
                FriendListItem item = mFriendList.get(position);
                if(delete){
                    confirmationDialog(position);
                }else{ //TODO challenge functionality
                    item.setFriendName(item.getFriendName() + "X");
                    mAdapter.notifyItemChanged(position);
                }
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
}
