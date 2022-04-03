package com.example.hashhunter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.security.NoSuchAlgorithmException;

/**
 * @References QR code scanner/decoder from Yuriy Budiyev https://github.com/yuriy-budiyev/code-scanner
 * License: MIT License
 * Copyright (c) 2017 Yuriy Budiyev [yuriy.budiyev@yandex.ru]
 *
 * Opens scanner which uses Camera, after getting permissions, to get a string from a qrcode
 */
public class ScanActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    //private boolean cameraPerms = false;
    private Integer points;
    private String qrCodeString;
    private byte[] byteHash;



    public static final String EXTRA_SCANNED_UNAME = "com.example.hashhunter.scanned_uname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_layout);
        Button continueButton = findViewById(R.id.continue_button);


        Intent intent = getIntent();

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // QR code string stored in result.getText()

                       // https://stackoverflow.com/questions/4967799/how-to-know-the-calling-activity-in-android
                        // Do different actions sepending on which activity called the scanner
                        if(getCallingActivity() != null) {
                            // Scan login code
                            if (getCallingActivity().getClassName().equals(LoginActivity.class.getName())) {
                                String uname = result.getText().toString();
                                intent.putExtra(EXTRA_SCANNED_UNAME, uname);
                                setResult(RESULT_OK, intent);
                                String pointMessage = "The username is " + uname;
                                Toast.makeText(ScanActivity.this, pointMessage, Toast.LENGTH_LONG).show();
                                finish();
                            // Scan code to delete
                            } else if (getCallingActivity().getClassName().equals(DeleteGameCodeActivity.class.getName())) {
                                String code = result.getText().toString();
                                intent.putExtra(EXTRA_SCANNED_UNAME, code);
                                setResult(RESULT_OK, intent);
                                String pointMessage = "The QR Code is " + code;
                                Toast.makeText(ScanActivity.this, pointMessage, Toast.LENGTH_LONG).show();
                                finish();
                            // Scan code to search player
                            } else if (getCallingActivity().getClassName().equals(ExploreActivity.class.getName())) {
                                String uname = result.getText().toString();
                                intent.putExtra(EXTRA_SCANNED_UNAME, uname);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }

                        // Get decoded string and hash, send hash and points to submit activity
                        else {
                            try {
                                byteHash = GameCodePointsController.getHashedCode(result.getText());
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }
                            qrCodeString = GameCodePointsController.toHexString(byteHash);
                            points = GameCodePointsController.getCodePoints(qrCodeString);
                            String pointMessage = "This QR is worth " + points + " Points";
                            Toast.makeText(ScanActivity.this, pointMessage, Toast.LENGTH_LONG).show();
                            continueButton.setVisibility(View.VISIBLE);
                        }

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });


        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScanActivity.this, ScanSubmitActivity.class);
                intent.putExtra("points", points);

                intent.putExtra("qrcode string", qrCodeString);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

}