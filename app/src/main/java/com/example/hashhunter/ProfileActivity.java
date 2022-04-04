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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.zxing.WriterException;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Shows the profile information of a player, including all GameCodes scanned.
 */
public class ProfileActivity extends AppCompatActivity implements QRAdapter.OnQRListener, AdapterView.OnItemSelectedListener {

    //implements QRAdapter.OnItemClickListener
    int columns = 2;
    //Set QR list, adapter, and grid manager
    private RecyclerView QRRecycler;
    private ImageView profilePic;
    public QRAdapter QRRecycleAdapter;
    private GridLayoutManager QRGridManager = new GridLayoutManager(this, columns);
    private Button profileCodeButton;
    private Button loginCodeButton;
    private AlertDialog.Builder codeDialogBuilder;
    public TextView usernameView;
    public TextView PointAmount;
    public TextView emailView;
    private ArrayList<GameCodeController> qrList;
    public static final Integer RESULT_RESTART = 3;
    private FirestoreController dbController = new FirestoreController();
    private DocumentReference refDoc;
    private static DocumentSnapshot userDoc;
    private String email;
    private String userName;
    private String playerCode;
    public TextView totalCodes;
    String ownerID;
    Map<String, Object> myData;
    String userNameCode = "com.example.hashhunter.username";
    String EmailCode ="com.example.hashhunter.email";
    String UniqueIDCode ="com.example.hashhunter.unique_id";
    String gameCodeListCode = "gameCodeList";
    private String uniqueID;
    private PlayerDataController playerController;
    final static ArrayList<String> myArray = new ArrayList<>();
    Spinner sortSpinner;
    TextView OtherProfilePointAmount;
    TextView OtherProfileTotalScanned;
    public static ProfileActivity profileInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Set the layout manager
        profileInstance = this;
        //Do profile pic
        profilePic = findViewById(R.id.profilePicture);

        usernameView = findViewById(R.id.username);
        emailView = findViewById(R.id.email);
        profilePic.setImageResource(R.drawable.ic_android);
        qrList = new ArrayList<>();
        profileCodeButton = findViewById(R.id.profileCodeButton);
        loginCodeButton = findViewById(R.id.loginCodeButton);



        sortSpinner = findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> sortStringAdapter = ArrayAdapter.createFromResource(this, R.array.sortOptions, R.layout.spiner_item);
        sortStringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortStringAdapter);

        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Retrieve unique id, from intent if available
        uniqueID = preferences.getString(PREF_UNIQUE_ID, null);
        ownerID = preferences.getString(PREF_UNIQUE_ID, null);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            uniqueID = extras.getString("userId");
            Log.d("PROFILE_DEBUG", "userId: " + uniqueID);
        }
        System.out.println(PREF_UNIQUE_ID);
        if (!uniqueID.equals(ownerID)) {
            loginCodeButton.setVisibility(View.GONE);
        }
        //Obtain all the other information from username


        ArrayList<Comment> testComments = new ArrayList<>();
        ArrayList<Integer> LocPicResources = new ArrayList<>();




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


    /**
     * Show login/profile code
     * @param code
     * @param buttonCode
     */
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

    /**
     * Show QR image for profile/login code
     * @param code
     * @return
     */
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
        //Start spinner
        sortSpinner.setOnItemSelectedListener(profileInstance);

        //This is a neat little trick to make it load once the recycler view has items.
        //Now I don't got to implement another class

    }

    /**
     * Open QR visualiser activity for selected QR code
     * @param position
     */
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

    /**
     * Get all info for the profile to display
     */
    public void loadProfileInfo(){
        this.getUserInfo();
        this.getPlayerStats();
        this.loadQRCodes();

    }

    /**
     * Get information about player to display on profile
     */
    private void getUserInfo(){
        dbController.getUserInfo(uniqueID).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot docRef = task.getResult();
                    Map<String, Object> myData = docRef.getData();
                    System.out.println(myData);
                    if (myData != null) {
                        email = (String) myData.get(EmailCode);
                        System.out.println(email);
                        userName = (String) myData.get(userNameCode);
                        playerCode = (String) myData.get(uniqueID);


                        usernameView.setText(userName);
                        if (ownerID == uniqueID);
                            emailView.setText(email);

                    }
                    //Not proud of this, but haven't found out do this outside the method

                    //Maybe they really don't want us to retrieve info outside this for some reason

                    //For now set the text view to this
                    //Though I will have to change this later after submission
                    //I really tried getting this part outside of the code but it did not let me.


                } else {
                    Log.d(TAG, "Cached get failed: ", task.getException());
                }
            }
        });


    }

    /**
     * Load QR codes to be viewed on the profile
     */
    private void loadQRCodes(){
        dbController.getPlayers(uniqueID).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot doc = task.getResult();
                    Map<String, Object> myData = doc.getData();
                    System.out.println(myData);
                    //This has to change

                    //Try to implement it using a recycler view
                    //Obtain player data, specifically we want the gameCodeList
                    if (myData != null) {
                        ArrayList<String> myCodes = (ArrayList<String>) myData.get(gameCodeListCode);
                        System.out.println(myCodes);


                        //We have all the game codes here
                        //For each game code owned by the user

                        dbController.getGameCodeList().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                                }
                                setUpRecycler(myControllers);
                                startSpinner();
                            }
                        });

                    }


                }
            }
        });


    }

    /**
     * Get total points and # of codes scanned for the player to display
     */
    private void getPlayerStats(){
        //https://firebase.google.com/docs/firestore/query-data/get-data
        //Obtan user snapshot
        //https://firebase.google.com/docs/firestore/query-data/listen#java
        System.out.println("Before obtaining player stats");
        System.out.println(uniqueID);
        dbController.getPlayerDoc(uniqueID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    System.out.println("FAILED!");
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }
                if (value != null && value.exists()) {
                    System.out.println("Success!");

                    Player player = value.toObject(Player.class);
                    Integer points = player.getTotalPoints();
                    System.out.println(points);
                    Integer codeTotal = player.getTotalGameCode();
                    System.out.println("Here?");
                    if (!uniqueID.equals(ownerID)){
                        System.out.println("HERE!");
                        /*OtherProfilePointAmount = findViewById(R.id.OtherAcountPointAmount);
                        OtherProfileTotalScanned = findViewById(R.id.OtherAcountQRScanned);
                        OtherProfileTotalScanned.invalidate();


                        OtherProfilePointAmount.setText("Total points: " + points.toString());
                        OtherProfileTotalScanned.setText(" Codes scanned: "+codeTotal.toString() );*/
                        PointAmount = findViewById(R.id.pointAmount);
                        totalCodes = findViewById(R.id.codeScannedAmount);
                        totalCodes.setText(" Codes scanned: "+codeTotal.toString() );
                        PointAmount.setText("Total points: " + points.toString());


                    }
                    else{

                        PointAmount = findViewById(R.id.pointAmount);
                        totalCodes = findViewById(R.id.codeScannedAmount);
                        totalCodes.setText("Codes scanned: "+codeTotal.toString() );
                        PointAmount.setText("Total points: " + points.toString());


                    }

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });


    }

    /**
     * Sort the QR list by ascending or descending and update
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (i ==0){
            QRRecycleAdapter.sortAscending();
            QRRecycleAdapter.notifyDataSetChanged();
        } else if (i == 1){
            QRRecycleAdapter.sortDescending();
            QRRecycleAdapter.notifyDataSetChanged();
        }
        System.out.println("-------------------------Option---------------------------");
        System.out.println(i);
        System.out.println("-------------------------Option End---------------------------");

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void startSpinner(){
        sortSpinner.setOnItemSelectedListener(this);
        //This helps the list to sort automatically after the recycler is loaded
        sortSpinner.setSelection(1);
    }
    public Integer getPoints(){
        String p = PointAmount.getText().toString();
        p = p.replace("Total points: ", "");
        Integer points = Integer.parseInt(p);

        return points;
    }

}