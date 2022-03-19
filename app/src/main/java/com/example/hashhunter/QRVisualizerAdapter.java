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

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class QRVisualizerAdapter extends RecyclerView.Adapter<QRVisualizerAdapter.QRVisualizerViewHolder>{
    private ArrayList<PhotoController> QRLocPicList;


    public QRVisualizerAdapter(ArrayList<PhotoController> somePhotos){
        this.QRLocPicList = somePhotos;
    }
    @NonNull
    @Override
    public QRVisualizerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.qr_visualizer_pic, parent, false);

        QRVisualizerViewHolder VisHolder =  new QRVisualizerViewHolder(myView);

        return VisHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull QRVisualizerViewHolder holder, int position) {
        //Get controller position
        PhotoController pController = QRLocPicList.get(position);
        pController.displayImage(holder.LocPicView);

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
