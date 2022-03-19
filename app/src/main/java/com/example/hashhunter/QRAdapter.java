package com.example.hashhunter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class QRAdapter extends RecyclerView.Adapter<QRAdapter.qrViewHolder> {
    private ArrayList<GameCodeController> qrList;
    private AdapterView.OnItemClickListener listener;



    public QRAdapter(ArrayList<GameCodeController> controllerList){
        qrList = controllerList;
    }
    @NonNull
    @Override


    public qrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_list_item, parent, false);

        qrViewHolder qrHolder =  new qrViewHolder(myView);

        return qrHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull qrViewHolder holder, int position) {
        GameCodeController myController = qrList.get(position);
        myController.SyncController();
        holder.treeView.setImageResource(R.drawable.ic_android);
        System.out.println(myController.getTitle());
        holder.titleView.setText(myController.getTitle());
        holder.pointsView.setText("Points: "+ myController.getPoints().toString());


    }

    @Override
    public int getItemCount() {
        return qrList.size();
    }


    public class qrViewHolder extends RecyclerView.ViewHolder{
        public ImageView treeView;
        public TextView pointsView;
        public TextView titleView;

        public qrViewHolder(@NonNull View itemView) {
            super(itemView);
            treeView = itemView.findViewById(R.id.treeImage);
            pointsView = itemView.findViewById(R.id.points);
            titleView = itemView.findViewById(R.id.qrTitle);
           // https://www.youtube.com/watch?v=3WR4QAiVuCw
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null){
                        System.out.println("Click!");
                    }
                }
            });
        }

    }

}
