package com.example.hashhunter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.WriterException;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

/**
 * This is the activity for registering a new user
 * It allows them to enter their username and email, checks if it is
 * valid, then registers them as a new user and starts the Dashboard activity
 */
public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "com.example.hashhunter.RegisterActivity";
    private static final String KEY_UNAME = "com.example.hashhunter.username";
    private static final String KEY_EMAIL = "com.example.hashhunter.email";
    private SharedPreferences sharedPreferences;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText usernameEdit;
    private EditText emailEdit;
    private String unique_id;
    private Button submitButton;
//    private ImageView qrCodeIV;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEdit = findViewById(R.id.username_edit_text);
        emailEdit = findViewById(R.id.email_edit_text);
//        qrCodeIV = findViewById(R.id.idIVQrcode);

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREF_NAME, MODE_PRIVATE);
                unique_id = sharedPreferences.getString(MainActivity.PREF_UNIQUE_ID, "IDNOTFOUND");

                // generate a qr Code
                //https://www.geeksforgeeks.org/how-to-generate-qr-code-in-android/
                Boolean validInput = validateInput(username, email);
                if (!validInput) {
                    // if the edittext inputs are empty then execute
                    // this method showing a toast message.
                    Toast.makeText(RegisterActivity.this, "Enter valid input", Toast.LENGTH_SHORT).show();
                } else {
                    // below is the line for getting the window manager service
//                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
//
//                    // initializing a variable for the default display
//                    Display display = manager.getDefaultDisplay();
//
//                    // creating the variable for the point that is to be displayed in QRCode
//                    Point point = new Point();
//                    display.getSize(point);
//
//                    // getting width and height of a point
//                    int width = point.x;
//                    int height = point.y;
//
//                    // generating dimension from width and height
//                    int dimen = Math.min(width, height);
//                    dimen = (dimen * 3) / 4;
//                    // setting these dimensions inside our qrcode generator to generate the qr code
//                    qrgEncoder = new QRGEncoder(unique_id, null, QRGContents.Type.TEXT, dimen);
//                    try {
//                        // getting our qrCode in the form of a bitmap
//                        bitmap = qrgEncoder.encodeAsBitmap();
//
//                        // setting the bitmap inside our imageView
//                        qrCodeIV.setImageBitmap(bitmap);
//
//                    } catch (WriterException e) {
//                        // this method is called for exception handling
//                        Log.e("Tag", e.toString());
//                    }

                    // still need to check and deny if the user already exists
                    //  https://firebase.google.com/docs/firestore/query-data/get-data#java_4
                    DocumentReference userDocRef = db.collection("Usernames").document(username);
                    userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Toast.makeText(RegisterActivity.this, "Username already exists!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // https://www.youtube.com/watch?v=MILE4PVx1kE&list=PLrnPJCHvNZuDrSqu-dKdDi3Q6nM-VUyxD&index=2
                                    Map<String, Object> info = new HashMap<>();
                                    info.put(KEY_UNAME, username);
                                    info.put(KEY_EMAIL, email);
                                    info.put(MainActivity.PREF_UNIQUE_ID, unique_id);
                                    db.collection("UserInfo").document(unique_id).set(info)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(RegisterActivity.this, "added to db", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(RegisterActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                                    Log.d(TAG, e.toString());
                                                }
                                            });

                                    info = new HashMap<>();
                                    info.put(KEY_UNAME, username);
                                    db.collection("Usernames").document(username).set(info)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(RegisterActivity.this, "added to db", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(RegisterActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                                    Log.d(TAG, e.toString());
                                                }
                                            });

                                    Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                    // Should this be called?
                                    //finish();
                                }
                            } else {
                                Log.d(TAG, "get failed with", task.getException());
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * Check if the information entered by the user is valid or not
     * @param username
     * username that was entered by the user
     * @param email
     * email that was entered by the user
     * @return
     * true if the input is valid (non-empty and unique username and valid email address), false otherwise
     */
    public Boolean  validateInput(String username, String email) {
        //https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
        Boolean ans = true;
     //https://www.javatpoint.com/java-email-validation
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            email = email.replaceAll("\\s+", "");
            if (email.length() > 0) {
                Toast.makeText(RegisterActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                ans = false;
            }
        }

        if (username.equals("") ) {
            // check that none of the fields are empty
            Toast.makeText(RegisterActivity.this, "Invalid username!", Toast.LENGTH_SHORT).show();
            ans = false;
        }

        return ans;
    };
}