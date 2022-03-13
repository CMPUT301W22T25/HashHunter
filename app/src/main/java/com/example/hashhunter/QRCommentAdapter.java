package com.example.hashhunter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QRCommentAdapter extends RecyclerView.Adapter<QRCommentAdapter.CommentViewHolder>{
    private ArrayList<QRComment> CommentList;
    private Context myContext;

    public QRCommentAdapter(ArrayList<QRComment> myItems){

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
        QRComment myComment = CommentList.get(position);
        holder.QRUserName.setText(myComment.getUserName());
        holder.myQRComment.setText(myComment.getComment());

    }

    @Override
    public int getItemCount() {
        return CommentList.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        TextView QRUserName;
        TextView myQRComment;

        public CommentViewHolder(View visualizerItem){
            super(visualizerItem);
            QRUserName = visualizerItem.findViewById(R.id.CommentUserName);
            myQRComment = visualizerItem.findViewById(R.id.CommentContent);

        }



    }
}
