package com.example.hashhunter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class QRVisualizerActivity extends AppCompatActivity {
    RecyclerView CommentRecycler;
    RecyclerView LocPicRecycler;
    QRCommentAdapter commentAdapter;
    FirestoreRecyclerAdapter LocPicAdapter;
    LinearLayoutManager myLayoutManager;
    LinearLayoutManager myHorizontalLayoutManager;
    ArrayList<String> PhotoCodes;
    ArrayList<Integer> LocPicResources;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference refDoc;
    private DocumentSnapshot userDoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrvisualizer);
        Intent intent =getIntent();
        GameCodeController codeController = intent.getParcelableExtra("QR ITEM");

        PhotoCodes = codeController.getPhotos();
        System.out.println(PhotoCodes);

        Query photosQuery = db.collection("MockPhotos").whereIn("uniqueID", PhotoCodes);

        LoadHorizontalRecycler(photosQuery);

        //qrComments = myItem.getQRComments();
        System.out.println("On this activity now!");






    }
    private void LoadHorizontalRecycler(Query query){
        FirestoreRecyclerOptions<PhotoController> options = new FirestoreRecyclerOptions.Builder<PhotoController>()
                .setQuery(query, PhotoController.class)
                .setLifecycleOwner(this)
                .build();
        LocPicRecycler = findViewById(R.id.LocationPicRecycler);

        LocPicAdapter = new QRVisualizerAdapter(options);
        System.out.println(LocPicAdapter.getSnapshots());
        myHorizontalLayoutManager = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);

        LocPicRecycler.setLayoutManager(myHorizontalLayoutManager);
        LocPicRecycler.setAdapter(LocPicAdapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(LocPicRecycler);

    }

    private void LoadCommentRecycler(Query query){
        /*CommentRecycler = findViewById(R.id.commentRecycler);

        commentAdapter = new QRCommentAdapter(qrComments);



        myLayoutManager = new LinearLayoutManager(this);
        myHorizontalLayoutManager = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);

        CommentRecycler.setLayoutManager(myLayoutManager);
        CommentRecycler.setAdapter(commentAdapter);*/

    }
}