package com.example.hashhunter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ProfileActivity extends AppCompatActivity {


    int columns = 2;
    //Set QR list, adapter, and grid manager
    private RecyclerView QRRecycler;
    private ImageView profilePic;
    private RecyclerView.Adapter QRRecycleAdapter;
    private GridLayoutManager QRGridManager = new GridLayoutManager(this, columns);
    private Button profileCodeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Set the layout manager

        //Do profile pic
        profilePic = findViewById(R.id.profilePicture);

        profilePic.setImageResource(R.drawable.ic_android);
        ArrayList<QRItem> qrList =  new ArrayList<>();
        profileCodeButton = findViewById(R.id.profileCodeButton);
        qrList.add(new QRItem(R.drawable.ic_launcher_background, "420 points", "Potato QR" ));
        qrList.add(new QRItem(R.drawable.ic_android, "360 points", "Tomato QR" ));
//        qrList.add(new QRItem(R.drawable.ic_baseline_account_tree_24, "69 points", "Tomato QR" ));

//        qrList.add(new QRItem(R.drawable.ic_baseline_child_care_24, "369 points", "Tomacco QR" ));
//        qrList.add(new QRItem(R.drawable.ic_baseline_face_24, "369 points", "Tomacco QR" ));
//        qrList.add(new QRItem(R.drawable.ic_baseline_4g_plus_mobiledata_24, "4g points", "Illuminati QR" ));

        qrList.add(new QRItem(R.drawable.ic_launcher_background, "420 points", "Potato QR" ));
        qrList.add(new QRItem(R.drawable.ic_android, "360 points", "Tomato QR" ));

//        qrList.add(new QRItem(R.drawable.ic_baseline_account_tree_24, "69 points", "Tomato QR" ));
//
//        qrList.add(new QRItem(R.drawable.ic_baseline_child_care_24, "369 points", "Tomacco QR" ));
//        qrList.add(new QRItem(R.drawable.ic_baseline_face_24, "369 points", "Tomacco QR" ));
//        qrList.add(new QRItem(R.drawable.ic_baseline_4g_plus_mobiledata_24, "4g points", "Illuminati QR"));
        QRRecycler = findViewById(R.id.treeList);

        QRRecycler.setLayoutManager(QRGridManager);

        QRRecycleAdapter = new QRAdapter(qrList);

        QRRecycler.setAdapter(QRRecycleAdapter);
        QRRecycler.setHasFixedSize(true);




        //Figure out which button was sent to this activity

            //If someone searched for someone elses profile then this is going to be sent with a different code

        //In case the user wants to check their own profile

            //Load the users data

        //Regardless of whos profile it is we are going to need 4 things

        //Profile picture, user name, profile code, tree amount and point amount

        //Player code is optional(Eg if I'm checking someone elses profile), so we will set it to null by default.

//        profileCodeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openCodeDialog(R.drawable.ic_android, "Profile Button");
//            }
//        });

        //Put it into the profile data model

        //Update the appropiate views (Within the model)

        //There are technically two types of profile activities here. When the user clicks on their own profile
        // And when the user clicks on someone elses profile


        //profileData class will only be used to load the data from the data base that we are allowed to access

        //Therefore it won't be necessary to create more than one profile class


    }

    public void openCodeDialog(Integer image, String buttonCode) {
//        Inflater inflater = LayoutInflater(R.layout.activity_profile, null);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);


        ImageView dialogCode;
        dialogCode = findViewById(R.id.imageCode);
        dialogCode.setImageResource(image);
        dialogBuilder.setTitle("buttonCode");

        AlertDialog dialog = dialogBuilder.create();

        dialog.setCancelable(true);
        dialog.setContentView(dialogCode);

        dialog.show();

    }

}