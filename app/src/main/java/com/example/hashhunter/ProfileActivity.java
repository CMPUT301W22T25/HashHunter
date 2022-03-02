package com.example.hashhunter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {


    private RecyclerView qrTreeRecycler;
    private RecyclerView.Adapter qrTreeAdapter;
    int columns = 2;
    //Set QR list, adapter, and grid manager
    private RecyclerView QRList;
    private RecyclerView.Adapter QRAdapter;
    private GridLayoutManager QRGridManager = new GridLayoutManager(this, columns);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Set the layout manager
        QRList.setLayoutManager(QRGridManager);

        ArrayList<QRItem> qrList =  new ArrayList<>();


        qrList.add(new QRItem(R.drawable.ic_launcher_background, 1, "Potato QR" ));
        qrList.add(new QRItem(R.drawable.ic_android, 1, "Tomato QR" ));




    }
}