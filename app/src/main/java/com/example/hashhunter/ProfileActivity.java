package com.example.hashhunter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {


    int columns = 2;
    //Set QR list, adapter, and grid manager
    private RecyclerView QRRecycler;
    private ImageView profilePic;
    private RecyclerView.Adapter QRRecycleAdapter;
    private GridLayoutManager QRGridManager = new GridLayoutManager(this, columns);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Set the layout manager

        //Do profile pic
        profilePic = findViewById(R.id.profilePicture);

        profilePic.setImageResource(R.drawable.ic_android);
        ArrayList<QRItem> qrList =  new ArrayList<>();

        qrList.add(new QRItem(R.drawable.ic_launcher_background, "420 points", "Potato QR" ));
        qrList.add(new QRItem(R.drawable.ic_android, "360 points", "Tomato QR" ));
        qrList.add(new QRItem(R.drawable.ic_baseline_account_tree_24, "69 points", "Tomato QR" ));

        qrList.add(new QRItem(R.drawable.ic_baseline_child_care_24, "369 points", "Tomacco QR" ));
        qrList.add(new QRItem(R.drawable.ic_baseline_face_24, "369 points", "Tomacco QR" ));
        qrList.add(new QRItem(R.drawable.ic_baseline_4g_plus_mobiledata_24, "4g points", "Illuminati QR" ));

        qrList.add(new QRItem(R.drawable.ic_launcher_background, "420 points", "Potato QR" ));
        qrList.add(new QRItem(R.drawable.ic_android, "360 points", "Tomato QR" ));
        qrList.add(new QRItem(R.drawable.ic_baseline_account_tree_24, "69 points", "Tomato QR" ));

        qrList.add(new QRItem(R.drawable.ic_baseline_child_care_24, "369 points", "Tomacco QR" ));
        qrList.add(new QRItem(R.drawable.ic_baseline_face_24, "369 points", "Tomacco QR" ));
        qrList.add(new QRItem(R.drawable.ic_baseline_4g_plus_mobiledata_24, "4g points", "Illuminati QR"));
        QRRecycler = findViewById(R.id.treeList);

        QRRecycler.setLayoutManager(QRGridManager);

        QRRecycleAdapter = new QRAdapter(qrList);

        QRRecycler.setAdapter(QRRecycleAdapter);
        QRRecycler.setHasFixedSize(true);

    }
}