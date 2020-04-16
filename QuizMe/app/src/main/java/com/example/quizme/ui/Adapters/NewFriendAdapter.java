package com.example.quizme.ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizme.R;
import com.example.quizme.ui.items.NewFriendItem;

import java.util.ArrayList;

// Adapter is used to connect the recycler view layout to the activity
// A part of this code was found on the web
public class NewFriendAdapter extends RecyclerView.Adapter<NewFriendAdapter.UsersListViewHolder> {
    private ArrayList<NewFriendItem> mUsersList;
    private NewFriendAdapter.OnItemClickListener mListener;

    // Used for event listeners evoked on the list
    public interface OnItemClickListener {
        void onAddClick(int position);
    }

    public void setOnItemClickListener(NewFriendAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


    public static class UsersListViewHolder extends RecyclerView.ViewHolder{
        public TextView mUserName;
        public ImageView mAddButton;

        // View for the RecyclerView list
        public UsersListViewHolder(View itemView, NewFriendAdapter.OnItemClickListener listener ){
            super(itemView);
            mAddButton = (ImageView) itemView.findViewById(R.id.add);
            mUserName = (TextView) itemView.findViewById(R.id.user_textView);

            // Delete button handler
            mAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            // Sendi true inn í Activityið ef ýtt var á delete takkann
                            listener.onAddClick(position);
                        }
                    }
                }
            });
        }
    }

    // This was found on the web needed for connection between layouts
    public NewFriendAdapter(ArrayList<NewFriendItem> usersList) {
        mUsersList = usersList;
    }

    @NonNull
    @Override
    public NewFriendAdapter.UsersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_friend_item, parent, false);
        NewFriendAdapter.UsersListViewHolder evh = new NewFriendAdapter.UsersListViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewFriendAdapter.UsersListViewHolder holder, int position) {
        NewFriendItem currentItem = mUsersList.get(position);
        holder.mUserName.setText(currentItem.getUsersName());
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }
}
