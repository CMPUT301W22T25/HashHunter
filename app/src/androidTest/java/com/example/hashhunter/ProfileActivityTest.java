package com.example.hashhunter;

import static android.content.Context.MODE_PRIVATE;
import static com.example.hashhunter.MainActivity.PREF_UNIQUE_ID;
import static com.example.hashhunter.MainActivity.PREF_USERNAME;
import static com.example.hashhunter.MainActivity.SHARED_PREF_NAME;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;





public class ProfileActivityTest {
    private Solo solo;
    SharedPreferences preferences;
    String OldId;
    private  FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TestUserId ="d9169874-42b2-4fe1-961a-2083f7515e98";
    public String createFakePlayer(String username){
        String uniqueId;
        //Add user info into the data base
        DocumentReference doc = db.collection("UserInfo").document();

        uniqueId = doc.getId();
        Log.d("id", uniqueId);
        System.out.println("My unique id is");
        System.out.println(uniqueId);
        String email = "meat@gmail.com";
        HashMap<String, Object> data = new HashMap<>();
        data.put("email", email);
        data.put("unique_id", uniqueId);
        data.put("username", username);
        //Add player with same document id in the database

        doc.set(data);
        //Update a bunch of fake gamecodes
        HashMap<String, Object> playerData = new HashMap<>();
        //We create a fake player into the database so we can test it

        playerData.put("username", username);
        playerData.put("displayTotal", 0);
        playerData.put("maxGameCodePoints", 0);
        playerData.put("totalGameCode", 0);
        playerData.put("profileCode", null);
        playerData.put("playerCode", null);

        ArrayList<String> arr = new ArrayList<>();
        playerData.put("gameCodeList", arr);

        DocumentReference playerDoc = db.collection("Players").document(uniqueId);
        playerDoc.set(playerData);

        return uniqueId;

    }
    public String FakeAddGameCode(String title, String code, String uniqueId, Integer points) {
        //We add a set of game codes to the player into the database in order to test
        DocumentReference gameCodeDoc = db.collection("GameCode").document();
        String  gameCodeId = gameCodeDoc.getId();
        HashMap<String, Object>  codeData = new HashMap<>();
        codeData.put("code", code);
        codeData.put("commentAmount", 0);
        ArrayList<String> arr = new ArrayList<>();
        codeData.put("comments", arr);
        double latitude = 21.0120391212;
        double longitude = 69.6969696969;
        codeData.put("latitude", latitude);
        codeData.put("longitude", longitude);
        ArrayList<String> owners = new ArrayList<>();
        owners.add(uniqueId);
        codeData.put("owners", owners);
        ArrayList<String> photos = new ArrayList<>();
        codeData.put("photos", photos);
        codeData.put("points", points);
        codeData.put("title", title);
        gameCodeDoc.set(codeData);

        updatePlayer(gameCodeId, points);
        return gameCodeId;
    }


    @Rule
    public ActivityTestRule<ProfileActivity> rule = new ActivityTestRule<>(ProfileActivity.class, true, true);
    /**
    * Runs before all tests and creates solo instance.
            * @throws Exception
    */

    public void setSharedPrefs(String id) {
        ProfileActivity p= rule.getActivity();
        preferences = p.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        //Store old id
        OldId = preferences.getString(PREF_UNIQUE_ID, "");
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_UNIQUE_ID,id);
        editor.apply();


    }
    @Before
    public void setUp() throws Exception{

        setSharedPrefs(TestUserId);

        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());

    }


    /**
     * Gets the Activity
    * @throws Exception
    */
    @Test
    public void start(){
        Activity activity = rule.getActivity();
    }

    @Test
    public void TestUsername(){
        //Create our fake mock player
        ProfileActivity p= rule.getActivity();
        p.getApplicationContext();
        SharedPreferences sharedPreferences = p.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String userName = "ProfileActivityTest";
        String id= sharedPreferences.getString(PREF_UNIQUE_ID, "");

        solo.assertCurrentActivity("Wrong", p.getClass());

        p.loadProfileInfo();

        assertEquals(userName, p.usernameView.getText());


        solo.waitForActivity("wqeklqwe");

    }

    @Test
    public void TestEmail(){
        String email = "dontDelete@gmail.com";
        ProfileActivity p= rule.getActivity();
        assertEquals(email, p.emailView.getText());
    }


    @Test
    public void TestPointUpdate(){
        FirestoreController dbController = new FirestoreController();
        ProfileActivity p= rule.getActivity();
        String strPoints = p.PointAmount.getText().toString();
        strPoints = strPoints.replace("Total points: ", "");
        Integer points = Integer.parseInt(strPoints);
        Integer pointsToAdd = 10;
        System.out.println("Points?");
        System.out.println(points);
        p.loadProfileInfo();
        String gameId = FakeAddGameCode("TestProfile", "wqe2eqw12", TestUserId, 10);
        String newPointsStr = p.PointAmount.getText().toString();
        newPointsStr = strPoints.replace("Total points: ", "");
        int newPoints = Integer.parseInt(newPointsStr);
        System.out.println("New points!");
        System.out.println(newPoints);
        solo.waitForActivity("wqe", 60);
        dbController.deletePlayerGameCodeReference(TestUserId, gameId);
        dbController.deleteGameCodeUsernameReference(TestUserId, gameId);


    }

    private void updatePlayer(String gameCodeID, int points){
        db.collection("Players").document(TestUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
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
                    }
                });

    }

    @After
    public void RestoreUser(){
        setSharedPrefs(OldId);
    }


}
