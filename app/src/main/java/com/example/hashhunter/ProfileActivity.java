package com.example.hashhunter;

import static android.content.ContentValues.TAG;
import static com.example.hashhunter.MainActivity.PREF_UNIQUE_ID;
import static com.example.hashhunter.MainActivity.PREF_USERNAME;
import static com.example.hashhunter.MainActivity.SHARED_PREF_NAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements QRAdapter.OnQRListener{

    //implements QRAdapter.OnItemClickListener
    int columns = 2;
    //Set QR list, adapter, and grid manager
    private RecyclerView QRRecycler;
    private ImageView profilePic;
    private QRAdapter QRRecycleAdapter;
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
    public static final Integer RESULT_RESTART = 3;
    private FirestoreController dbController = new FirestoreController();
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

        PointAmount = findViewById(R.id.pointAmount);

        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Retrieve unique id, from intent if available
        uniqueID = preferences.getString(PREF_UNIQUE_ID, null);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            uniqueID = extras.getString("userId");
            Log.d("PROFILE_DEBUG", "userId: " + uniqueID);
        }

        //Obtain all the other information from username


        //https://firebase.google.com/docs/firestore/query-data/get-data
        //Obtan user snapshot



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

        loadProfileInfo();



        //Create the profile stuff click listener
       // https://www.youtube.com/watch?v=69C1ljfDvl0
        //https://www.geeksforgeeks.org/how-to-generate-qr-code-in-android/
        profileCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // setting this dimens
                openCodeDialog(userName, "Profile Code");
            }
        });


        loginCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCodeDialog(uniqueID, "Login Code");


            }
        });


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

    private void setUpRecycler(ArrayList<GameCodeController> myControllerList) {
        System.out.println(myControllerList);
        QRRecycler = findViewById(R.id.treeList);

        QRRecycler.setLayoutManager(QRGridManager);

        QRRecycleAdapter = new QRAdapter(myControllerList, this);

        QRRecycler.setAdapter(QRRecycleAdapter);
    }

    @Override
    public void onQRClick(int position) {

        Intent intent = new Intent(this, QRVisualizerActivity.class);
        GameCodeController myCodeCont = QRRecycleAdapter.getItem(position);
        System.out.println("Attributes before passing!");
        //I feel filthy for doing this but it works
        intent.putExtra("USERNAME", usernameView.getText());
        intent.putExtra("QR ITEM", myCodeCont);
        intent.putExtra("USER ID", uniqueID);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_RESTART){
            recreate();


        }

    }

    public void loadProfileInfo(){
        this.getUserInfo();
        this.loadQRCodes();

    }

    private void getUserInfo(){
        dbController.getUserInfo(uniqueID).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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


    }
    private void loadQRCodes(){
        dbController.getPlayers(uniqueID).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot doc = task.getResult();
                    System.out.println(doc);
                    Player player = doc.toObject(Player.class);
                    Integer points = player.getTotalPoints();
                    PointAmount.setText("Total points: " + points.toString());
                    Map<String, Object> myData = doc.getData();
                    System.out.println(myData);
                    //This has to change

                    //Try to implement it using a recycler view
                    //Obtain player data, specifically we want the gameCodeList
                    if (myData != null) {
                        ArrayList<String> myCodes = (ArrayList<String>) myData.get(gameCodeListCode);
                        System.out.println(myCodes);


                        //We have all the game codes here

                        CollectionReference gameCodesRef = db.collection("GameCode");

                        //For each game code owned by the user

                        gameCodesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                //Check every gameCode
                                //https://stackoverflow.com/questions/66834816/how-to-get-arraylist-of-custom-objects-from-firestore-documents
                                //Use some of the code there
                                ArrayList<GameCodeController> myControllers = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()){
                                    //If the document matches the id
                                    String myID = document.getId();
                                    if (myCodes.contains(myID)) {
                                        //Create new gamecode and set up its controller
                                        GameCode myCode = document.toObject(GameCode.class);
                                        GameCodeController myController = new GameCodeController(myCode);

                                        System.out.println("Document id "+ myID);
                                        myController.setDataBasePointer(myID);
                                        //Add controller
                                        myControllers.add(myController);
                                    }
                                    //Initialize recycler view
                                    setUpRecycler(myControllers);
                                }

                            }
                        });

                        //Reconstruct the GameCode object

                        //Add it to an array


                        //For each gamecode in the collection

                        //If the gamecode is equals to my gamecode






                        //Print them to the screen

                        //Only get the gamecodes whose name is the same as the gamecodes in my list


                        //Pass it onto the recycler view



                        //setUpRecycler(myQuery);
                    }


                }
            }
        });


    }
}