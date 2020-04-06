package com.example.quizme.ui.Adapters;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizme.R;
import com.example.quizme.ui.FriendListActivity;
import com.example.quizme.ui.items.FriendListItem;

import java.util.ArrayList;

// Adapter is used to connect the recycler view layout to the activity
// A part of this code was found on the web
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {
    private ArrayList<FriendListItem> mFriendList;
    private OnItemClickListener mListener;

    // Used for event listeners evoked on the list
    public interface OnItemClickListener {
        void onItemClick(int position, boolean delete);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class FriendListViewHolder extends RecyclerView.ViewHolder{
        public ImageView mDeleteButton;
        public TextView mUserName;
        public Button mChallengeButton;

        // View for the RecyclerView list
        public FriendListViewHolder(View itemView, OnItemClickListener listener ){
            super(itemView);
            mDeleteButton = (ImageView) itemView.findViewById(R.id.delete);
            mChallengeButton = (Button) itemView.findViewById(R.id.challenge_button);
            mUserName = (TextView) itemView.findViewById(R.id.user_textView);

            // If the challenge button is pressed we challenge a friend
            mChallengeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position, false);
                        }
                    }
                }
            });

            // Delete button handler
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            //Sendi true inn í Activityið ef ýtt var á delete takkann
                            listener.onItemClick(position, true);
                        }
                    }
                }
            });

        }
    }

    // This was found on the web needed for connection between layouts
    public FriendListAdapter(ArrayList<FriendListItem> friendList) {
        mFriendList = friendList;
    }

    @NonNull
    @Override
    public FriendListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
        FriendListViewHolder evh = new FriendListViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListViewHolder holder, int position) {
        FriendListItem currentItem = mFriendList.get(position);

        holder.mUserName.setText(currentItem.getFriendName());
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }
}
