package com.example.hashhunter;

import static android.content.ContentValues.TAG;
import static com.example.hashhunter.MainActivity.PREF_UNIQUE_ID;
import static com.example.hashhunter.MainActivity.SHARED_PREF_NAME;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements QRAdapter.OnItemClickListener{


    int columns = 2;
    //Set QR list, adapter, and grid manager
    private RecyclerView QRRecycler;
    private ImageView profilePic;
    private FirestoreRecyclerAdapter QRRecycleAdapter;
    private GridLayoutManager QRGridManager = new GridLayoutManager(this, columns);
    private Button profileCodeButton;
    private Button loginCodeButton;
    private AlertDialog.Builder codeDialogBuilder;
    private TextView usernameView;
    private TextView TreeAmount;
    private TextView PointAmount;
    private TextView emailView;
    private ArrayList<GameCodeController> qrList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference refDoc;
    private static DocumentSnapshot userDoc;
    private String email;
    private String userName;
    private String playerCode;
    Map<String, Object> myData;
    String userNameCode = "com.example.hashhunter.username";
    String EmailCode ="com.example.hashhunter.email";
    String UniqueIDCode ="com.example.hashhunter.unique_id";
    String gameCodeListCode = "gameCodeList";
    private String uniqueID;
    private PlayerDataController playerController;
    final static ArrayList<String> myArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Set the layout manager

        //Do profile pic
        profilePic = findViewById(R.id.profilePicture);

        usernameView = findViewById(R.id.username);
        emailView = findViewById(R.id.email);
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







       // docRef = db.collection("UserInfo").document(uniqueID);

        Source source = Source.CACHE;
        refDoc = db.collection("UserInfo").document(uniqueID);

        //Might redesign this, not happy with the result
        refDoc.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 if (task.isSuccessful()) {
                     // Document found in the offline cache
                     DocumentSnapshot docRef = task.getResult();

                     Map<String, Object> myData = docRef.getData();
                     System.out.println(myData);
                     email = (String) myData.get(EmailCode);
                     System.out.println(email);

                     //Not proud of this, but haven't found out do this outside the method

                     //Maybe they really don't want us to retrieve info outside this for some reason
                     userName = (String) myData.get(userNameCode);
                     playerCode = (String) myData.get(uniqueID);

                     //For now set the text view to this
                     //Though I will have to change this later after submission
                     //I really tried getting this part outside of the code but it did not let me.
                     usernameView.setText(userName);
                     emailView.setText(email);


                 } else {
                     Log.d(TAG, "Cached get failed: ", task.getException());
                 }
             }
         });

        System.out.println(myArray);

        DocumentReference playerDoc = db.collection("MockPlayers").document(uniqueID);


        playerDoc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              if (task.isSuccessful()) {
                  // Document found in the offline cache
                  DocumentSnapshot doc = task.getResult();
                  System.out.println(doc);

                  Map<String, Object> myData = doc.getData();
                  System.out.println(myData);

                  //Obtain player data, specifically we want the gameCodeList
                  if (myData != null) {
                      ArrayList<String> myCodes = (ArrayList<String>) myData.get(gameCodeListCode);
                      System.out.println(myCodes);
                      Query myQuery = db.collection("GameCode").whereIn("docPointer", myCodes);
                      setUpRecycler(myQuery);
                  }


              }
          }
      });

        //Figure out which button was sent to this activity

            //If someone searched for someone elses profile then this is going to be sent with a different code

        //In case the user wants to check their own profile

            //Load the users data

        //Regardless of whos profile it is we are going to need 4 things

        //Profile picture, user name, profile code, tree amount and point amount

        //Player code is optional(Eg if I'm checking someone elses profile), so we will set it to null by default.


        //Create the profile stuff click listener
       // https://www.youtube.com/watch?v=69C1ljfDvl0
        //https://www.geeksforgeeks.org/how-to-generate-qr-code-in-android/
        profileCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // setting this dimens
                openCodeDialog(uniqueID, "Profile Code");
            }
        });


        loginCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCodeDialog(uniqueID, "Login Code");


            }
        });
        //Put it into the profile data model

        //Update the appropiate views (Within the model)

        //There are technically two types of profile activities here. When the user clicks on their own profile
        // And when the user clicks on someone elses profile


        //PlayerDataController class will only be used to load the data from the data base that we are allowed to access

        //Therefore it won't be necessary to create more than one profile class


    }



    public void openCodeDialog(String code, String buttonCode) {
        LayoutInflater inflater = getLayoutInflater();
        View codeDialogLayout = inflater.inflate(R.layout.profilecodedialog, null);
        codeDialogBuilder = new AlertDialog.Builder(this);
        codeDialogBuilder.setCancelable(true);


        ImageView codeImage = codeDialogLayout.findViewById(R.id.imageCode);

        codeImage.setImageBitmap(convertToQr(code));
        codeDialogBuilder.setView(codeDialogLayout);
        codeDialogBuilder.setTitle(buttonCode);

        codeDialogBuilder.setCancelable(true);
        codeDialogBuilder.show();
    }

   /* @Override
    public void onQRClick(int position) {

        Intent intent = new Intent(this, QRVisualizerActivity.class);
        GameCodeController myCurrentItem = (GameCodeController) QRRecycleAdapter.getItem(position);
        intent.putExtra("QR ITEM", myCurrentItem);
        startActivity(intent);


    } */
    public Bitmap convertToQr(String code) {
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Bitmap bitmap = null;
        Display display = manager.getDefaultDisplay();

        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;


        QRGEncoder qrgEncoder = new QRGEncoder(code, null, QRGContents.Type.TEXT, dimen);
        // getting our qrcode in the form of bitmap.
        try {
             bitmap = qrgEncoder.encodeAsBitmap();

        } catch (WriterException e) {
            // this method is called for
            // exception handling.
            Log.e("Tag", e.toString());
            // view using .setimagebitmap method.
        }
        return bitmap;

    }

    private void setUpRecycler(Query query) {
        FirestoreRecyclerOptions<GameCodeController> options = new FirestoreRecyclerOptions.Builder<GameCodeController>()
                .setQuery(query, GameCodeController.class)
                .setLifecycleOwner(this)
                .build();

        QRRecycler = findViewById(R.id.treeList);

        QRRecycler.setLayoutManager(QRGridManager);

        QRRecycleAdapter = new QRAdapter(options, this::onItemClick);
        QRRecycler.setAdapter(QRRecycleAdapter);
        QRRecycler.setHasFixedSize(true);
    }


    @Override
    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
        Intent intent = new Intent(this, QRVisualizerActivity.class);
        GameCodeController myCurrentItem = documentSnapshot.toObject(GameCodeController.class);
        intent.putExtra("QR ITEM", myCurrentItem);
        startActivity(intent);

    }
}