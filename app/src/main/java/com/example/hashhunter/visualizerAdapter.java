package com.example.hashhunter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class visualizerAdapter extends RecyclerView.Adapter<visualizerAdapter.visualizerViewHolder>{
    private ArrayList<QRItem> QRItemList;
    private Context myContext;

    public visualizerAdapter(Context context, ArrayList<QRItem> myItems){
        QRItemList = myItems;

        myContext = context;


    }
    @NonNull
    @Override
    public visualizerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull visualizerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class visualizerViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;


        public visualizerViewHolder(View visualizerItem){
            super(visualizerItem);
            itemImage = visualizerItem.findViewById(R.id.visualizerImage);

        }



    }
}
