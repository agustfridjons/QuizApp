package com.example.quizme.ui.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizme.R;
import com.example.quizme.ui.items.EasyModeItem;

import java.util.ArrayList;

// Adapter is used to connect the recycler view layout to the activity
// A part of this code was found on the web
public class EasyModeAdapter extends RecyclerView.Adapter<EasyModeAdapter.HintListViewHolder> {
    private ArrayList<EasyModeItem> mHintList;
    private EasyModeAdapter.OnItemClickListener mListener;

    // Used for event listeners evoked on the list
    public interface OnItemClickListener {
        void onAddClick(int position);
    }

    public void setOnItemClickListener(EasyModeAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


    public static class HintListViewHolder extends RecyclerView.ViewHolder {
        public TextView mViewQuestion;
        public TextView mViewAnswer;
       // public Button mStartGame;

        // View for the RecyclerView list
        public HintListViewHolder(View itemView, EasyModeAdapter.OnItemClickListener listener) {
            super(itemView);
            mViewQuestion = (TextView) itemView.findViewById(R.id.view_question);
            mViewAnswer = (TextView) itemView.findViewById(R.id.view_answer);
           // mStartGame = (Button) itemView.findViewById(R.id.start_game_button);

            // Delete button handler
           /* mStartGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            // Sendi true inn í GameActivity ef ýtt var á Start game takkann
                            listener.onAddClick(position);
                        }
                    }
                }
            });*/
        }
    }

    // This was found on the web needed for connection between layouts
    public EasyModeAdapter(ArrayList<EasyModeItem> hintList) {
        mHintList = hintList;
    }

    @NonNull
    @Override
    public EasyModeAdapter.HintListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hint_list_item, parent, false);
        EasyModeAdapter.HintListViewHolder evh = new EasyModeAdapter.HintListViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull HintListViewHolder holder, int position) {
        EasyModeItem currentItem = mHintList.get(position);
        holder.mViewQuestion.setText(currentItem.getQuestion());
        System.out.println("SKRRRRR SPURNING " + currentItem.getQuestion() + " OOOG SVAR: " + currentItem.getAnswer());
        holder.mViewAnswer.setText(currentItem.getAnswer());
    }

    @Override
    public int getItemCount() {
        return mHintList.size();
    }
}