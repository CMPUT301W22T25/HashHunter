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
import java.util.Collections;

/**
 * Adapter to display QR code as a list in the ProfileActivity
 */
public class QRAdapter extends RecyclerView.Adapter<QRAdapter.qrViewHolder> {
    private ArrayList<GameCodeController> qrList;
    private OnQRListener myAdapterListener;

    /**
     * constructor for QRAdapter
     * @param controllerList
     * @param myListener
     */
    public QRAdapter(ArrayList<GameCodeController> controllerList,OnQRListener myListener){
      this.myAdapterListener = myListener;
      this.qrList = controllerList;
    }

    /**
     * getter for GameCodeController
     * @param position
     * @return GameCodeController controller to interact with GameCode object
     */
    public GameCodeController getItem(int position){
        if (position < this.qrList.size()) {
            return this.qrList.get(position);
        }
        else{
            return null;
        }
    }
    @NonNull
    @Override

    /**
     * create a view for recycler view
     */
    public qrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tree_list_item, parent, false);

        qrViewHolder qrHolder =  new qrViewHolder(myView, myAdapterListener);

        return qrHolder;
    }

    /**
     * utility functions for recycler view to select item from qrList and display it
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull qrViewHolder holder, int position) {
        GameCodeController myController = qrList.get(position);
        myController.SyncController();
        holder.treeView.setImageResource(R.drawable.ic_baseline_qr_code_scanner_24);
        System.out.println(myController.getTitle());
        holder.titleView.setText(myController.getTitle());
        holder.pointsView.setText("Points: "+ myController.getPoints().toString());


    }

    /**
     * return size of qrList array
     * @return
     */
    @Override
    public int getItemCount() {
        return qrList.size();
    }

    /**
     * nested class to create container for qr code
     */
    public class qrViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView treeView;
        public TextView pointsView;
        public TextView titleView;
        public OnQRListener qrListener;

        /**
         * constructor for qrViewHolder
         * @param itemView
         * @param listener
         */
        public qrViewHolder(@NonNull View itemView, OnQRListener listener) {
            super(itemView);
            treeView = itemView.findViewById(R.id.treeImage);
            pointsView = itemView.findViewById(R.id.points);
            titleView = itemView.findViewById(R.id.qrTitle);
           // https://www.youtube.com/watch?v=3WR4QAiVuCw

            this.qrListener = listener;
            itemView.setOnClickListener(this);
        }

        /**
         * specify what to do when qr view holder is clicked
         * @param view
         */
        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                qrListener.onQRClick(position);
            }
        }
    }

    /**
     * interface to create on click listener for the qr view container
     */
    public interface OnQRListener{
        void onQRClick(int position);
    }

    /**
     * customize ascending sorting of qrList
     */
    public void sortAscending(){
        Collections.sort(qrList, new CustomComparator());
    }

    /**
     * customize descending sorting of qrList
     */
    public void sortDescending(){
        Collections.sort(qrList, new CustomComparator());
        Collections.reverse(qrList);

    }



}
