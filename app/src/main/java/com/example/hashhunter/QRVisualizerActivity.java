package com.example.hashhunter;


import static com.example.hashhunter.MainActivity.PREF_UNIQUE_ID;
import static com.example.hashhunter.MainActivity.PREF_USERNAME;
import static com.example.hashhunter.MainActivity.SHARED_PREF_NAME;
import static com.example.hashhunter.ProfileActivity.RESULT_RESTART;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Activity to show information of qr codes from the profile or on the map
 */
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
    private FirestoreController dbController = new FirestoreController();
    private DocumentReference refDoc;
    private DocumentSnapshot userDoc;
    ConstraintLayout myConstLayout;
    GameCodeController myController;
    String playerId;
    Button deleteVisButton;
    TextView locationView;

    String ownerID;
    String ownerUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrvisualizer);
        Intent intent = getIntent();
        myController = intent.getParcelableExtra("QR ITEM");
        username = intent.getStringExtra("USERNAME");
        playerId = intent.getStringExtra("USER ID");


        myConstLayout = findViewById(R.id.visLayout);
        textBoxView = findViewById(R.id.textBox);
        sendButton = findViewById(R.id.sendButton);
        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ownerID = preferences.getString(PREF_UNIQUE_ID, null);
        ownerUser = preferences.getString(PREF_USERNAME, null);
        //Initial set up of gamecode controller
        System.out.println("Attributes succesfully passed!");
        //Synchronize the controller with the items on gamecode;
        myController.SyncController();

        titleView = findViewById(R.id.gamecodeTitle);
        pointView = findViewById(R.id.gamecodePoints);
        amountScanView = findViewById(R.id.gameCodeScanAmount);

        //Retrieve unique id, from intent if available

        System.out.println(myController.getPoints());
        titleView.setText(myController.getTitle());
        pointView.setText(myController.getPoints().toString()+" Points");
        amountScanView.setText("Scanned by " + myController.getOwnerAmount()+ " people");
        //Close keyboard if user clicks outside the screen


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (myController.getLatitude() != null  && myController.getLongitude() != null) {
            getLocation(myController.getLatitude(), myController.getLongitude());
        }
        myConstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard();
            }
        });
        /**
         * click button to add comment
         */
        //This buttons function is to send a comment
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Once the button is clicked
            public void onClick(View view) {
                //Check if text view is not empty
                String myComment = textBoxView.getText().toString();
                //If the length of the comment in edit text view is more than 0 then send the comment into the database
                if ( myComment.length() > 0){
                    Comment comment = new Comment(ownerUser, myComment);
                    CommentController theComController = new CommentController(comment);
                    //Update recycler view
                    commentControllers.add(theComController);
                    commentAdapter.notifyDataSetChanged();
                    //Add comemnt to gamecode
                    myController.addComment(comment);
                    //Scroll to last position in order to observe new comment
                    CommentRecycler.scrollToPosition(commentAdapter.getItemCount()-1);
                    //Close keyboard
                    closeKeyboard();
                    //Clear edit text view
                    textBoxView.getText().clear();

                }

            }
        });




        initializeComments(myController);
        initializePhotos(myController);


        //Now we have to be able to delete the qr code from the if the user wants to


        //So the user will press the card view  view for a long time

        //Then a delete prompt will come up

        //Do you want to delete qr code? yes, no


        //If yes then...
        System.out.println("Owner");
        System.out.println(ownerID);
        System.out.println("Maybe owner");
        System.out.println(playerId);
        //Check if the user is the owner of the qr code
        CardView cardHolder = findViewById(R.id.codeCardHolder);
        if (playerId.equals(ownerID)) {
            System.out.println("Owner went here");
            cardHolder.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Dialog dialog = new Dialog(QRVisualizerActivity.this);
                    dialog.setContentView(R.layout.deletedialog);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.show();
                    deleteVisButton = dialog.findViewById(R.id.delete_visualizer_button);
                    deleteVisButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            deleteCode();
                            setResult(RESULT_RESTART);
                            finish();
                        }
                    });
                    return false;
                }
            });
        }
            //If the user is the owner of the qr code then delete the entire qr code




    }


    /**
     * loads horizontal recycler view to display photos
     * @param theControllers
     *      a list of photocontrollers that display images from url
     */
    private void LoadHorizontalRecycler(ArrayList<PhotoController> theControllers){

        LocPicRecycler = findViewById(R.id.LocationPicRecycler);

        LocPicAdapter = new QRVisualizerAdapter(theControllers);
        myHorizontalLayoutManager = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);

        LocPicRecycler.setLayoutManager(myHorizontalLayoutManager);
        LocPicRecycler.setAdapter(LocPicAdapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(LocPicRecycler);

    }
    /**
     * loads recycler view to display comments
     * @param qrComments
     *      a list of comments with owners
     */
    private void LoadCommentRecycler(ArrayList<CommentController> qrComments){
        CommentRecycler = findViewById(R.id.commentRecycler);
        commentAdapter = new QRCommentAdapter(qrComments);


        myLayoutManager = new LinearLayoutManager(this);
        myHorizontalLayoutManager = new LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false);

        CommentRecycler.setLayoutManager(myLayoutManager);
        CommentRecycler.setAdapter(commentAdapter);

    }

    //https://www.youtube.com/watch?v=CW5Xekqfx3I
    /**
     * gets all comments that are related to the qr code
     * @param myController
     *          controller for qr code
     */
    private void initializeComments(GameCodeController myController){

        dbController.getCommentList().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
    /**
     * gets all photos that are related to the qr code
     * @param myController
     *          controller for qr code
     */
    public void initializePhotos(GameCodeController myController){
        //Obtain collection reference

        dbController.getPhotoList().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

    private void deleteCode(){
        ArrayList<String> owners = myController.getOwners();

        if (owners.size() > 0){
            SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);

            String gameCodeId = myController.getDataBasePointer();
            System.out.println("----------code--------------");
            System.out.println(playerId);
            System.out.println("----------code end--------------");

            dbController.deleteGameCodeUsernameReference(gameCodeId, playerId);
            dbController.deletePlayerGameCodeReference(playerId, gameCodeId);
            //Just delete the qr code from the players reference.

            //Delete all comments which the player has made

            //Leave back to profile view


        }


    }
    /**
     * gets more specific data such as city and country based on latitude and longitude
     * @param latitude
     *        location data corresponding to latitude
     * @param longitude
     *        location data corresponding to latitude
     */
    public void getLocation(double latitude, double longitude ) {

        Geocoder geocoder = new Geocoder(QRVisualizerActivity.this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        String cityName = addresses.get(0).getLocality();
        String countryName = addresses.get(0).getCountryName();

        locationView = findViewById(R.id.gameCodeLocation);
        locationView.setText(cityName +", " + countryName );



    }

}