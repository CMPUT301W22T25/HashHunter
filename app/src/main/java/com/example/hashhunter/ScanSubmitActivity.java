package com.example.hashhunter;


import static android.content.ContentValues.TAG;
import static com.example.hashhunter.MainActivity.PREF_UNIQUE_ID;
import static com.example.hashhunter.MainActivity.PREF_USERNAME;
import static com.example.hashhunter.MainActivity.SHARED_PREF_NAME;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.maps.model.LatLng;
import com.google.android.libraries.maps.model.MarkerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * Has fields for user to submit qrcode title, location, and photo.
 * Then creates or updates current GameCode in database
 */
public class ScanSubmitActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1; // Request code for camera permissions
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    private Bitmap photoBitmap; // bitmap received from camera app
    private Location qrcodeLocation; // Current location that user provides

    private String photoId; // id of photo in firestore
    private String gameCodeId; // id og gamecode in firestore
    private Integer points; // value of points
    private String code; // hashed representation of qrcode
    // keep track of code location
    private Double latitude;
    private Double longitude;

    private String uniqueID; // id of user accessing app
    private String username; // username of user accessing app

    private boolean gamecodeExists = false; // flag to see if gamecode in firestore

    private LocationManager locationManager;
    /**
     * When add button is pressed, get location and show details
     */
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            qrcodeLocation = location;

            latitude = qrcodeLocation.getLatitude();
            longitude = qrcodeLocation.getLongitude();
            Geocoder geocoder = new Geocoder(ScanSubmitActivity.this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(qrcodeLocation.getLatitude(), qrcodeLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String cityName = addresses.get(0).getLocality();
            String stateName = addresses.get(0).getAdminArea();
            String countryName = addresses.get(0).getCountryName();

            TextView showLocation = findViewById(R.id.current_location);
            showLocation.setText("City: " + cityName + "\nState: " + stateName + "\nCountry: " + countryName);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_submit);

        Intent intent = getIntent();
        points = (Integer) intent.getSerializableExtra("points");
        code = intent.getStringExtra("qrcode string");

        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        uniqueID = preferences.getString(PREF_UNIQUE_ID, null);
        username = preferences.getString(PREF_USERNAME, null);

        TextView showPoints = findViewById(R.id.qr_code_points);
        showPoints.setText(points + " Points");

        Button addPhoto = findViewById(R.id.add_photo_button);
        Button addLocation = findViewById(R.id.add_location_button);
        Button saveButton = findViewById(R.id.save_button);



        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getLocationPermissions())
                    getCurrentLocation();

            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * Upload photo to firebase storage ✅
                 * Add photo object containing url to firestore ✅
                 * Get database of GameCodes
                 * Check if a code with the same string and location exists
                 * If yes, add that gamecode to the current player and increase numPlayers by 1
                 * If not, construct a new GameCode object and add to player GameCodeList, and to
                 * database if it has a location
                 */

                if (photoBitmap != null) {
                    // need to wait for photos to be uploaded, then upload code data
                    uploadPhotoToStorage();
                }
                if (qrcodeLocation != null) {
                    /**
                     * Just store latitude and longitude in FireStore
                     * When getting a location from a GameCode in Firestore:
                     * Make a new location and set longitude/latitude, then get distance
                     */
                    FirestoreController.getGameCodeListWithCode(code).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                if(document.get("latitude") == null) continue;
                                Location checkLocation = new Location("");
                                checkLocation.setLatitude((Double) document.get("latitude"));
                                checkLocation.setLongitude((Double) document.get("longitude"));
                                if(checkLocation.distanceTo(qrcodeLocation) <= 10) {
                                    // Game Codes are the same
                                    document.getReference().update("owners", FieldValue.arrayUnion(uniqueID));
                                    if (photoId != null){
                                        document.getReference().update("photos", FieldValue.arrayUnion(photoId));
                                    }
                                    gameCodeId = document.getId();
                                    updatePlayer(gameCodeId);
                                    Toast.makeText(ScanSubmitActivity.this, "GameCode Exists, adding existing GameCode to player", Toast.LENGTH_SHORT).show();
                                    gamecodeExists = true;
                                    break;
                                }
                            }
                            if(gamecodeExists == false){
                                storeGameCodeInDB();
                                Toast.makeText(ScanSubmitActivity.this, "Added new GameCode", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("Error occurred", String.valueOf(task.getException()));
                        }
                    });

                }

                else  {
                    // directly upload code data
                    storeGameCodeInDB();
                    Toast.makeText(ScanSubmitActivity.this, "Added new GameCode", Toast.LENGTH_SHORT).show();

                }

                Intent intent = new Intent(ScanSubmitActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * If location permissions are not granted, request for them.
     * @return
     * True if permissions already granted
     */
    public boolean getLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(ScanSubmitActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(ScanSubmitActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(ScanSubmitActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);

            return false;
        }
        else return true;
    }

    /**
     * This is only called with location permissions, so it is safe to suppress permissions here
     *
     * Gets current location as a Android.Location object
     */
    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
    }

    /**
     * Store scanned qr code in the database
     */
    private void storeGameCodeInDB() {
        // retrieve title name
        EditText titleBox = findViewById(R.id.qr_code_name);
        String title = titleBox.getText().toString();
        // build game code
        GameCode newGameCode;
        if (photoId == null && qrcodeLocation == null) {
            newGameCode = new GameCode(title, code, points, uniqueID);
        } else if (photoId != null && qrcodeLocation == null) {
            newGameCode = new GameCode(title, code, points, photoId, uniqueID);
        } else if (photoId == null && qrcodeLocation != null) {
            newGameCode = new GameCode(title, code, points, uniqueID, latitude, longitude);
        } else {
            newGameCode = new GameCode(title, code, points, photoId, uniqueID, latitude, longitude);
        }
        gameCodeId = UUID.randomUUID().toString();
        FirestoreController.postGameCode(gameCodeId, newGameCode)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("DB_OPERATION", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("DB_OPERATION", "Error writing document", e);
                    }
                });
        // Add to player
        updatePlayer(gameCodeId);


    }

    private void updatePlayer(String gameCodeID){
        FirestoreController.getPlayers(uniqueID).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Player player = document.toObject(Player.class);
                    document.getReference().update("gameCodeList", FieldValue.arrayUnion(gameCodeID));
                    document.getReference().update("totalPoints", points+player.getTotalPoints());
                    document.getReference().update("totalGameCode", player.getTotalGameCode()+1);
                    if (points > player.getMaxGameCodePoints()) document.getReference().update("maxGameCodePoints", points);
                } else {
                    Log.d("Get player", "get failed with ", task.getException());
                }
        });
    }

    /**
     * Take image as a bitmap, convert it to byte array then upload it to firebase storage
     */
    private void uploadPhotoToStorage() {
        // construct byte array to be uploaded
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        // send packet to firebase storage
        String path = "photos/" + UUID.randomUUID() + ".png";
        StorageReference storageRef = storage.getReference();
        StorageReference pathRef = storageRef.child(path);
        UploadTask uploadTask = pathRef.putBytes(data); // return progress update
        // get the download url
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return pathRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // get url
                    Uri downloadUri = task.getResult();
                    // store url in db
                    storePhotoDataInDB(downloadUri.toString());
                } else {
                    // Handle failures
                }
            }
        });
    }

    /**
     * Store photo data like owner username and url into firestore "Photo" collection
     * GameCode object will store the id of Photo object to refer to the url
     * @param photoUrl url of photo to be stored
     */
    private void storePhotoDataInDB(String photoUrl) {
        Photo newPhoto = new Photo(photoUrl, username); // replace value with logged in user
        photoId = UUID.randomUUID().toString();
        FirestoreController.postPhoto(photoId, newPhoto);
        if (gameCodeId!=null) {
            FirestoreController.updateGameCodePhoto(gameCodeId, photoId);
        }
    }

    /**
     * Launch built-in camera app (permission is assumed)
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
            e.printStackTrace();
        }
    }

    /**
     * Received image from built-in camera app, store it and display it as a thumbnail
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photoBitmap = imageBitmap;
            // display preview
            ImageView imageView = findViewById(R.id.scan_submit_photo_preview);
            imageView.setImageBitmap(imageBitmap);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length == 2
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    getCurrentLocation();
                } else {


                    Toast.makeText(ScanSubmitActivity.this, "Permission denied to read your location", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }



}
