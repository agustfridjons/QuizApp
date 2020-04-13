package com.example.quizme.ui.Adapters;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizme.R;
import com.example.quizme.ui.items.FriendListItem;

import java.util.ArrayList;

// Adapter is used to connect the recycler view layout to the activity
// A part of this code was found on the web
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder> {
    private ArrayList<FriendListItem> mFriendList;
    private OnItemClickListener mListener;

    // Used for event listeners evoked on the list
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onChallengeClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class FriendListViewHolder extends RecyclerView.ViewHolder{
        public ImageView mDeleteButton, mChallengeNotification;
        public TextView mUserName;
        public Button mChallengeButton;

        // View for the RecyclerView list
        public FriendListViewHolder(View itemView, OnItemClickListener listener ){
            super(itemView);
            mDeleteButton = (ImageView) itemView.findViewById(R.id.delete);
            mChallengeNotification = (ImageView) itemView.findViewById(R.id.challenge_notification);
            mChallengeButton = (Button) itemView.findViewById(R.id.challenge_button);
            mUserName = (TextView) itemView.findViewById(R.id.user_textView);



            // If the challenge button is pressed we challenge a friend
            mChallengeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onChallengeClick(position);
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
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            // Challenger handler
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            //Sendi true inn í Activityið ef ýtt Card í listanum ef það inniheldur challenge notification
                            if(mChallengeNotification.getVisibility() == View.VISIBLE)
                                listener.onItemClick(position);
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
        holder.mChallengeNotification.setVisibility(View.GONE);
        holder.mUserName.setText(currentItem.getFriendName());
        if(currentItem.isChallenge())
            holder.mChallengeNotification.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }
}
