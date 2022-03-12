package com.example.hashhunter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VisualizerAdapter extends RecyclerView.Adapter<VisualizerAdapter.VisualizerViewHolder>{
    private ArrayList<QRItem> QRItemList;
    private Context myContext;

    public VisualizerAdapter(Context context, ArrayList<QRItem> myItems){
        QRItemList = myItems;
        myContext = context;


    }
    @NonNull
    @Override
    public VisualizerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qr_visualizer_item, parent, false);
        return new VisualizerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisualizerViewHolder holder, int position) {
        QRItem myItem = QRItemList.get(position);
        holder.itemImage.setImageResource(myItem.getTreeImage());

    }

    @Override
    public int getItemCount() {
        return QRItemList.size();
    }

    public class VisualizerViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView QRTitle;
        TextView QRLocation;
        TextView QRPoints;
        TextView QRStr;

        public VisualizerViewHolder(View visualizerItem){
            super(visualizerItem);
            itemImage = visualizerItem.findViewById(R.id.visualizerImage);
            QRTitle = visualizerItem.findViewById(R.id.TitleText);
            QRLocation = visualizerItem.findViewById(R.id.LocationText);
            QRPoints = visualizerItem.findViewById(R.id.QRPointText);

        }



    }
}
