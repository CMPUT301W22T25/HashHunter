package com.example.hashhunter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class QRAdapter extends FirestoreRecyclerAdapter<GameCodeController, QRAdapter.qrViewHolder> {
    private ArrayList<GameCode> qrList;
    private OnItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public QRAdapter(@NonNull FirestoreRecyclerOptions options, OnItemClickListener myListener) {
        super(options);
        listener = myListener;
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
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                        System.out.println("Click!");
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public qrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_list_item, parent, false);

        qrViewHolder qrHolder = new qrViewHolder(myView);
        return qrHolder;
    }


    @Override
    protected void onBindViewHolder(@NonNull qrViewHolder holder, int position, @NonNull GameCodeController model) {
        GameCodeController myItem =  (GameCodeController) model;

        holder.treeView.setImageResource(R.drawable.ic_android);

        holder.pointsView.setText("Points " + myItem.getPoints());


        holder.titleView.setText(myItem.getTitle());
    }


    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
