package com.example.hashhunter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Activity for deleting gamecodes and players. Has buttons that let the owner of the app (the admin) choose
 * what they want to do and redirect them to the appropriate activity
 */
public class OwnerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);

        Button deletePlayerButton = findViewById(R.id.delete_player_button);
        deletePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OwnerActivity.this, DeletePlayerActivity.class);
                startActivity(intent);
            }
        });

        Button deleteCodeButton = findViewById(R.id.delete_code_button);
        deleteCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start delete code activity
                Intent intent = new Intent(OwnerActivity.this, DeleteGameCodeActivity.class);
                startActivity(intent);
            }
        });
    }
}