package com.example.hashhunter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class QRVisualizerActivity extends AppCompatActivity {
    RecyclerView CommentRecycler;
    RecyclerView LocPicRecycler;
    QRCommentAdapter commentAdapter;
    QRVisualizerAdapter LocPicAdapter;
    LinearLayoutManager myLayoutManager;
    LinearLayoutManager myHorizontalLayoutManager;
    ArrayList<Comment> qrComments;
    ArrayList<Integer> LocPicResources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrvisualizer);
        Intent intent =getIntent();
        GameCodeController myItem = intent.getParcelableExtra("QR ITEM");

        //qrComments = myItem.getQRComments();
        //LocPicResources = myItem.getLocationPics();
        System.out.println("On this activity now!");

        CommentRecycler = findViewById(R.id.commentRecycler);
        LocPicRecycler = findViewById(R.id.LocationPicRecycler);

        commentAdapter = new QRCommentAdapter(qrComments);
        LocPicAdapter = new QRVisualizerAdapter(LocPicResources);
        myLayoutManager = new LinearLayoutManager(this);
        myHorizontalLayoutManager = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);

        CommentRecycler.setLayoutManager(myLayoutManager);
        CommentRecycler.setAdapter(commentAdapter);
        LocPicRecycler.setLayoutManager(myHorizontalLayoutManager);
        LocPicRecycler.setAdapter(LocPicAdapter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(LocPicRecycler);





    }
}