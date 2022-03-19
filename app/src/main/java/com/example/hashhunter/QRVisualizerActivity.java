package com.example.hashhunter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class QRVisualizerActivity extends AppCompatActivity {
    RecyclerView CommentRecycler;
    RecyclerView LocPicRecycler;
    QRCommentAdapter commentAdapter;
    QRVisualizerAdapter LocPicAdapter;
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

        GameCode myCode = intent.getParcelableExtra("QR ITEM");

        System.out.println("Attributes succesfully passed!");
        myCode.printAttributes();
        GameCodeController myController = new GameCodeController(myCode);
        //Synchronize the controller with the items on gamecode;
        myController.SyncController();

        //Now I want to be able to display the photos in my gamecode controller

        //Obtain collection reference
        CollectionReference myPhotosRef = db.collection("Photo");

        myPhotosRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<PhotoController> myPhotoControllers = new ArrayList<>();
                //Obtain the photos
                ArrayList<String> myPhotoCodes = myController.getPhotos();
                System.out.println("----------------My codes-------------");

                System.out.println(myPhotoCodes);
                System.out.println("----------------My data-------------");
                for (QueryDocumentSnapshot document : task.getResult()){
                    String myID = document.getId();
                    if (myPhotoCodes.contains(myID)){
                        //Create photo object
                        Photo thePhoto = document.toObject(Photo.class);
                        //Create controller and add it to the controller list
                        myPhotoControllers.add(new PhotoController(thePhoto));
                    }
                }
                LoadHorizontalRecycler(myPhotoControllers);
            }
        });







    }
    private void LoadHorizontalRecycler(ArrayList<PhotoController> theControllers){

        LocPicRecycler = findViewById(R.id.LocationPicRecycler);

        LocPicAdapter = new QRVisualizerAdapter(theControllers);
        myHorizontalLayoutManager = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);

        LocPicRecycler.setLayoutManager(myHorizontalLayoutManager);
        LocPicRecycler.setAdapter(LocPicAdapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(LocPicRecycler);

    }

    private void LoadCommentRecycler(Query query){
        CommentRecycler = findViewById(R.id.commentRecycler);
        ArrayList<Comment> qrComments = new ArrayList<>();
        commentAdapter = new QRCommentAdapter(qrComments);



        myLayoutManager = new LinearLayoutManager(this);
        myHorizontalLayoutManager = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);

        CommentRecycler.setLayoutManager(myLayoutManager);
        CommentRecycler.setAdapter(commentAdapter);

    }
}