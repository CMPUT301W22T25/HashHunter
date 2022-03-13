package com.example.hashhunter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

/**
 * @References QR code scanner/decoder from Yuriy Budiyev https://github.com/yuriy-budiyev/code-scanner
 * License: MIT License
 * Copyright (c) 2017 Yuriy Budiyev [yuriy.budiyev@yandex.ru]
 */
public class ScanActivity extends AppCompatActivity {
    private CodeScanner mCodeScanner;
    private final int CAMERA_REQUEST_CODE = 101;

    public static final String EXTRA_SCANNED_UNAME = "com.example.hashhunter.scanned_uname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner_layout);

        setupPermissions();

        Intent intent = getIntent();

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Show result of scanned text

                        Toast.makeText(ScanActivity.this, result.getText(), Toast.LENGTH_SHORT).show();

                        // https://stackoverflow.com/questions/4967799/how-to-know-the-calling-activity-in-android
                        if (getCallingActivity().getClassName().equals(LoginActivity.class.getName())) {
                            String uname = result.getText().toString();
                            intent.putExtra(EXTRA_SCANNED_UNAME, uname);
                            finish();
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

    private void setupPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != CAMERA_REQUEST_CODE)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
    }*/
}