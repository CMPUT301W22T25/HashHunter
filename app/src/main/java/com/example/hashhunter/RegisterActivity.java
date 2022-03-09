package com.example.hashhunter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEdit;
    private EditText emailEdit;
    private EditText passwordEdit;
    private Button submitButton;
    private ImageView qrCodeIV;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEdit = findViewById(R.id.username_edit_text);
        emailEdit = findViewById(R.id.email_edit_text);
        passwordEdit = findViewById(R.id.password_edit_text);
        qrCodeIV = findViewById(R.id.idIVQrcode);

        submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEdit.getText().toString();
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();

                // generate a qr Code
                //https://www.geeksforgeeks.org/how-to-generate-qr-code-in-android/
                Boolean isValid = validateInput(username, email, password);
                if (!isValid) {
                    // if the edittext inputs are empty then execute
                    // this method showing a toast message.
                    Toast.makeText(RegisterActivity.this, "Enter some text to generate QR Code", Toast.LENGTH_SHORT).show();
                } else {
                    // below is the line for getting the window manager service
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);

                    // initializing a variable for the default display
                    Display display = manager.getDefaultDisplay();

                    // creating the variable for the point that is to be displayed in QRCode
                    Point point = new Point();
                    display.getSize(point);

                    // getting width and height of a point
                    int width = point.x;
                    int height = point.y;

                    // generating dimension from width and height
                    int dimen = Math.min(width, height);
                    dimen = (dimen * 3) / 4;

                    // setting these dimensions inside our qrcode generator to generate the qr code
                    String qrCodeString = username + password;
                    qrgEncoder = new QRGEncoder(qrCodeString, null, QRGContents.Type.TEXT, dimen);
                    try {
                        // getting our qrCode in the form of a bitmap
                        bitmap = qrgEncoder.encodeAsBitmap();

                        // setting the bitmap inside our imageView
                        qrCodeIV.setImageBitmap(bitmap);

                    } catch (WriterException e) {
                        // this method is called for exception handling
                        Log.e("Tag", e.toString());
                    }

                }
            }
        });
    }

    public Boolean validateInput(String username, String email, String password) {
        //https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method

        Boolean result  = true;

        // https://www.javatpoint.com/java-email-validation
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (username.equals("") || email.equals("") || password.equals("")) {
            // check that none of the fields are empty
            result = false;
        } else if (!matcher.matches()) {
            // check that the email is valid
            result = false;
        }
        return result;
    };
}