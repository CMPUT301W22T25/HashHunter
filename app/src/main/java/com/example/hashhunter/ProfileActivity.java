package com.example.hashhunter;

import static android.content.ContentValues.TAG;
import static com.example.hashhunter.MainActivity.PREF_UNIQUE_ID;
import static com.example.hashhunter.MainActivity.SHARED_PREF_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements QRAdapter.OnQRListener{


    int columns = 2;
    //Set QR list, adapter, and grid manager
    private RecyclerView QRRecycler;
    private ImageView profilePic;
    private RecyclerView.Adapter QRRecycleAdapter;
    private GridLayoutManager QRGridManager = new GridLayoutManager(this, columns);
    private Button profileCodeButton;
    private Button loginCodeButton;
    private AlertDialog.Builder codeDialogBuilder;
    private TextView username;
    private TextView TreeAmount;
    private TextView PointAmount;
    private TextView Email;
    private ArrayList<GameCode> qrList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static DocumentSnapshot userDoc;
    private String email;
    private String userName;
    private String playerCode;
    Map<String, Object> myData;
    String userNameCode = "com.example.hashhunter.email";
    String EmailCode ="com.example.hashhunter.email";
    String UniqueIDCode ="com.example.hashhunter.email";
    private String uniqueID;
    private PlayerDataController playerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //Set the layout manager

        //Do profile pic
        profilePic = findViewById(R.id.profilePicture);

        profilePic.setImageResource(R.drawable.ic_android);
        qrList =  new ArrayList<>();
        profileCodeButton = findViewById(R.id.profileCodeButton);
        loginCodeButton = findViewById(R.id.loginCodeButton);



        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Retrieve unique id
        uniqueID = preferences.getString(PREF_UNIQUE_ID, null);


        //Obtain all the other information from username


        //https://firebase.google.com/docs/firestore/query-data/get-data
        //Obtan user snapshot








        //For example if we are searching for a user it will be different from logging in

        //Currently implementing our logged in user data


        //So depending where we are this will do a different thing


        //If the user is in another profile then we will not be able to fetch the login code

        //In this case it will be null

        //So if login code is null

          //Then set the left button as the profile code

        ArrayList<Comment> testComments = new ArrayList<>();
        ArrayList<Integer> LocPicResources = new ArrayList<>();

        LocPicResources.add(R.drawable.ic_android);
        LocPicResources.add(R.drawable.ic_baseline_4g_plus_mobiledata_24);
        LocPicResources.add(R.drawable.ic_baseline_child_care_24);
        LocPicResources.add(R.drawable.ic_launcher_background);

        //Show both buttons
        testComments.add(new Comment("Potato123", "I hate potatoes"));
        testComments.add(new Comment("lil Tay", "Strongest Flexer In da game"));
        testComments.add(new Comment("MasterCoder", "Crappy QR code bro step up ur game I am a master coder and I'll let u know that i graduated from master coder academy"));

        testComments.add(new Comment("MasterChief", "This ain't halo"));

        testComments.add(new Comment("Lil Peep", "What a sad qr code"));



        QRRecycler = findViewById(R.id.treeList);

        QRRecycler.setLayoutManager(QRGridManager);

        QRRecycleAdapter = new QRAdapter(qrList, this);

        QRRecycler.setAdapter(QRRecycleAdapter);
        QRRecycler.setHasFixedSize(true);

       // docRef = db.collection("UserInfo").document(uniqueID);

        Source source = Source.CACHE;

    // Get the document, forcing the SDK to use the offline cache
    /*    docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 if (task.isSuccessful()) {
                     // Document found in the offline cache
                     DocumentSnapshot docRef = task.getResult();
                     Map<String, Object> myData = docRef.getData();

                     email = (String) myData.get(EmailCode);
                     userName = (String) myData.get(userNameCode);
                     playerCode = (String) myData.get(uniqueID);
                 } else {
                     Log.d(TAG, "Cached get failed: ", task.getException());
                 }
             }
         });
       */

        //Figure out which button was sent to this activity

            //If someone searched for someone elses profile then this is going to be sent with a different code

        //In case the user wants to check their own profile

            //Load the users data

        //Regardless of whos profile it is we are going to need 4 things

        //Profile picture, user name, profile code, tree amount and point amount

        //Player code is optional(Eg if I'm checking someone elses profile), so we will set it to null by default.


        //Create the profile stuff click listener
       // https://www.youtube.com/watch?v=69C1ljfDvl0

        profileCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCodeDialog(R.drawable.ic_baseline_face_24, "Profile Code");
            }
        });


        loginCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCodeDialog(R.drawable.ic_baseline_child_care_24, "Login Code");


            }
        });
        //Put it into the profile data model

        //Update the appropiate views (Within the model)

        //There are technically two types of profile activities here. When the user clicks on their own profile
        // And when the user clicks on someone elses profile


        //PlayerDataController class will only be used to load the data from the data base that we are allowed to access

        //Therefore it won't be necessary to create more than one profile class


    }



    public void openCodeDialog(Integer image, String buttonCode) {
        LayoutInflater inflater = getLayoutInflater();
        View codeDialogLayout = inflater.inflate(R.layout.profilecodedialog, null);
        codeDialogBuilder = new AlertDialog.Builder(this);
        codeDialogBuilder.setCancelable(true);


        ImageView codeImage = codeDialogLayout.findViewById(R.id.imageCode);

        codeImage.setImageResource(image);
        codeDialogBuilder.setView(codeDialogLayout);
        codeDialogBuilder.setTitle(buttonCode);

        codeDialogBuilder.setCancelable(true);
        codeDialogBuilder.show();
    }

    @Override
    public void onQRClick(int position) {

        Intent intent = new Intent(this, QRVisualizerActivity.class);
        GameCode myCurrentItem = qrList.get(position);
        intent.putExtra("QR ITEM", myCurrentItem);
        startActivity(intent);


    }

}