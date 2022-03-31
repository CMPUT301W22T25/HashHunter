package com.example.hashhunter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

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
    ArrayList<CommentController> commentControllers = new ArrayList<>();
    TextView titleView;
    TextView pointView;
    TextView amountScanView;
    ArrayList<Integer> LocPicResources;
    EditText textBoxView;
    AppCompatButton sendButton;
    String username;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference refDoc;
    private DocumentSnapshot userDoc;
    ConstraintLayout myConstLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrvisualizer);
        Intent intent = getIntent();
        GameCodeController myController = intent.getParcelableExtra("QR ITEM");
        username = intent.getStringExtra("USERNAME");
        myConstLayout = findViewById(R.id.visLayout);
        textBoxView = findViewById(R.id.textBox);
        sendButton = findViewById(R.id.sendButton);

        //Initial set up of gamecode controller
        System.out.println("Attributes succesfully passed!");
        //Synchronize the controller with the items on gamecode;
        myController.SyncController();

        titleView = findViewById(R.id.gamecodeTitle);
        pointView = findViewById(R.id.gamecodePoints);
        amountScanView = findViewById(R.id.gameCodeScanAmount);

        System.out.println(myController.getPoints());
        titleView.setText(myController.getTitle());
        pointView.setText(myController.getPoints().toString()+" Points");
        amountScanView.setText("Scanned by " + myController.getOwnerAmount()+ " people");
        //Close keyboard if user clicks outside the screen



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myConstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check if text view is not empty
                String myComment = textBoxView.getText().toString();
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println(myComment);
                if ( myComment.length() > 0){
                    Comment comment = new Comment(username, myComment);
                    CommentController theComController = new CommentController(comment);

                    commentControllers.add(theComController);
                    commentAdapter.notifyDataSetChanged();
                    myController.addComment(comment, db);
                    CommentRecycler.scrollToPosition(commentAdapter.getItemCount()-1);
                    closeKeyboard();
                    textBoxView.getText().clear();

                }

            }
        });


        initializeComments(myController);
        initializePhotos(myController);


        //Now we will load the comments on each game code


        //Create comment functionality now


        //So we will have an edit text view


        //Once the user enters a comment we add the comment onto the database and the name of the user who made it



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

    private void LoadCommentRecycler(ArrayList<CommentController> qrComments){
        CommentRecycler = findViewById(R.id.commentRecycler);
        commentAdapter = new QRCommentAdapter(qrComments);


        myLayoutManager = new LinearLayoutManager(this);
        myHorizontalLayoutManager = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);

        CommentRecycler.setLayoutManager(myLayoutManager);
        CommentRecycler.setAdapter(commentAdapter);

    }

    //https://www.youtube.com/watch?v=CW5Xekqfx3I

    private void initializeComments(GameCodeController myController){
        CollectionReference myCommentsRef = db.collection("Comment");

        myCommentsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //Get comment codes and start comment controller list
                ArrayList<String> myCommentCodes = myController.getComments();
                //For each document in all comments
                for (QueryDocumentSnapshot document : task.getResult()){
                    String myID = document.getId();
                    //Check which one is our comment
                    if (myCommentCodes.contains(myID)) {
                        Comment theComment = document.toObject(Comment.class);
                        System.out.println(theComment.getOwner());
                        commentControllers.add(new CommentController(theComment));
                    }
                }
                LoadCommentRecycler(commentControllers);
            }
        });

    }

    public void initializePhotos(GameCodeController myController){
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
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String myID = document.getId();
                    if (myPhotoCodes.contains(myID)) {
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

    private void closeKeyboard(){
        View myView = getCurrentFocus();
        if (myView != null) {
            InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(myView.getWindowToken(), 0);
        }
    }

   // https://stackoverflow.com/questions/36433299/setdisplayhomeasupenabled-not-working-in-preferenceactivity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}