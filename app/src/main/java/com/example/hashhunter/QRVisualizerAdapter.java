package com.example.hashhunter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QRVisualizerAdapter extends RecyclerView.Adapter<QRVisualizerAdapter.QRVisualizerViewHolder>{
    private ArrayList<Integer> QRLocPicList;

    public QRVisualizerAdapter(ArrayList<Integer> myItems){

        QRLocPicList = myItems;


    }
    @NonNull
    @Override
    public QRVisualizerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qr_visualizer_pic, parent, false);
        return new QRVisualizerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QRVisualizerViewHolder holder, int position) {
        holder.LocPicView.setImageResource(QRLocPicList.get(position));
    }

    @Override
    public int getItemCount() {
        return QRLocPicList.size();
    }

    public class QRVisualizerViewHolder extends RecyclerView.ViewHolder{
        ImageView LocPicView;

        public QRVisualizerViewHolder(View visualizerItem){
            super(visualizerItem);
            LocPicView = visualizerItem.findViewById(R.id.LocationImage);

        }



    }

}
