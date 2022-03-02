package com.example.hashhunter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QRAdapter extends RecyclerView.Adapter<QRAdapter.qrViewHolder> {
    private ArrayList<QRItem> qrList;
    public static class qrViewHolder extends RecyclerView.ViewHolder {
        public ImageView treeView;
        public TextView pointsView;
        public TextView titleView;
        public qrViewHolder(@NonNull View itemView) {
            super(itemView);
            treeView = itemView.findViewById(R.id.treeImage);
            pointsView = itemView.findViewById(R.id.points);
            titleView = itemView.findViewById(R.id.qrTitle);


        }
    }

    @NonNull
    @Override
    public qrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_list_item, parent, false);

        qrViewHolder qrHolder =     new qrViewHolder(myView);
        return qrHolder;
    }


    public QRAdapter(ArrayList<QRItem> itemList){
        qrList = itemList;
    }
    @Override
    public void onBindViewHolder(@NonNull qrViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
