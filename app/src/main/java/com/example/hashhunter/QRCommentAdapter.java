package com.example.hashhunter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Class for displaying comments related to a qr code in a recycler view
 */
public class QRCommentAdapter extends RecyclerView.Adapter<QRCommentAdapter.CommentViewHolder>{
    private ArrayList<CommentController> CommentList;
    private Context myContext;

    /**
     * constructor for the qr comment adapter
     * @param myItems a list of comment controllers
     */
    public QRCommentAdapter(ArrayList<CommentController> myItems){

        CommentList = myItems;


    }
    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qr_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentController myComment = CommentList.get(position);
        holder.QRUserName.setText(myComment.getOwner());
        holder.myQRComment.setText(myComment.getComment());

    }
    /**
     * gets a specific comment
     * @Param desired index to fetch comment
     * @return desired comment controller
     */
    public CommentController getItem(int i){
        return CommentList.get(i);
    }

    /**
     * gets the number of comments related to a qr code
     * @return number of comments
     */
    @Override
    public int getItemCount() {
        return CommentList.size();
    }

    /**
     * A class for displaying the comment in a view
     */
    public class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView QRUserName;
        TextView myQRComment;

        /**
         * constructor for the class
         * @param visualizerItem a view for comment
         */
        public CommentViewHolder(View visualizerItem){
            super(visualizerItem);
            QRUserName = visualizerItem.findViewById(R.id.CommentUserName);
            myQRComment = visualizerItem.findViewById(R.id.CommentContent);

        }



    }
}
