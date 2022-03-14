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

public class QRVisualizerAdapter extends FirestoreRecyclerAdapter<PhotoController, QRVisualizerAdapter.QRVisualizerViewHolder>{
    private ArrayList<PhotoController> QRLocPicList;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public QRVisualizerAdapter(@NonNull FirestoreRecyclerOptions<PhotoController> options) {
        super(options);
    }


    @NonNull
    @Override
    public QRVisualizerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qr_visualizer_pic, parent, false);
        return new QRVisualizerViewHolder(view);

    }


    @Override
    protected void onBindViewHolder(@NonNull QRVisualizerViewHolder holder, int position, @NonNull PhotoController model) {
        model.displayImage(holder.LocPicView);

    }



    public class QRVisualizerViewHolder extends RecyclerView.ViewHolder{
        ImageView LocPicView;

        public QRVisualizerViewHolder(View visualizerItem){
            super(visualizerItem);
            LocPicView = visualizerItem.findViewById(R.id.LocationImage);

        }



    }

}
