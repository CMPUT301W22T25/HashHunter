package com.example.hashhunter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QRAdapter extends RecyclerView.Adapter<QRAdapter.qrViewHolder> {
    private ArrayList<GameCode> qrList;
    private OnQRListener bigQRListener;
    public static class qrViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView treeView;
        public TextView pointsView;
        public TextView titleView;
        OnQRListener onQRListener;
        public qrViewHolder(@NonNull View itemView, OnQRListener qrListener) {
            super(itemView);
            treeView = itemView.findViewById(R.id.treeImage);
            pointsView = itemView.findViewById(R.id.points);
            titleView = itemView.findViewById(R.id.qrTitle);

            this.onQRListener = qrListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onQRListener.onQRClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public qrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_list_item, parent, false);

        qrViewHolder qrHolder = new qrViewHolder(myView, bigQRListener);
        return qrHolder;
    }


    public QRAdapter(ArrayList<GameCode> itemList, OnQRListener qrListener){
        qrList = itemList;
        this.bigQRListener = qrListener;
    }
    @Override
    public void onBindViewHolder(@NonNull qrViewHolder holder, int position) {
        GameCode myItem =  qrList.get(position);

        holder.treeView.setImageResource(R.drawable.ic_android);

        holder.pointsView.setText("Points " + myItem.getPoints());


        holder.titleView.setText(myItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return qrList.size();
    }

    public interface OnQRListener{
        void onQRClick(int position);
    }
}
