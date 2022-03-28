package com.example.hashhunter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import org.w3c.dom.Text;

public class SearchActivity extends AppCompatActivity {

    private ImageView profilePic;
    private PlayerList playerList;
    private String name;
    private TextView searchText;
    private TextView username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("search");
        }

        searchText = (TextView) findViewById(R.id.search_text);
        username = (TextView) findViewById(R.id.search_username);

        searchText.setText("Search result for username '"+name+"'");
        username.setText(name);











    }
}
