package com.example.hashhunter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {

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




    }
}