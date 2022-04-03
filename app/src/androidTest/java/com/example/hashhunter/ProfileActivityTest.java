package com.example.hashhunter;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


class MockProfileActivity extends ProfileActivity{
    private  FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uniqueId;
    MockProfileActivity(){
        super();

        createFakePlayer("MrTester");

        ProfAddGameCode("crap code", "12jndoiqn12iondo2i1n9012je092dwqmklin3i");
        ProfAddGameCode("crap code1", "qweqweqlqwoop21");
        ProfAddGameCode("crap code2", "wqkdlqwlek12lkn12kjneekj1n dkjqw d");
        ProfAddGameCode("crap code3", "wqkdlqwlek12lkn12kjneekj1n dkjqw d");



    }
    public void createFakePlayer(String username){

        //Add user info into the data base
        DocumentReference doc = db.collection("UserInfo").document();

        uniqueId = doc.getId();
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

        String[] arr = {};
        playerData.put("gameCodeList", arr);

        DocumentReference playerDoc = db.collection("Players").document(uniqueId);
        playerDoc.set(playerData);



    }
    public void ProfAddGameCode(String title, String code) {
        //We add a set of game codes to the player into the database in order to test
        DocumentReference gameCodeDoc = db.collection("GameCode").document();
        String  gameCodeId = gameCodeDoc.getId();
        HashMap<String, Object>  codeData = new HashMap<>();
        codeData.put("code", code);
        codeData.put("commentAmount", 0);
        String[] arr = {};
        codeData.put("comments", arr);
        double latitude = 21.0120391212;
        double longitude = 69.6969696969;
        codeData.put("latitude", latitude);
        codeData.put("longitude", longitude);
        String[] owners = {uniqueId};
        codeData.put("owners", owners);
        String[] photos = {};
        codeData.put("photos", codeData);
        int points = 69420;
        codeData.put("points", points);
        codeData.put("title", title);
        gameCodeDoc.set(codeData);
    }

}


public class ProfileActivityTest {
    private Solo solo;

    public ActivityTestRule<MockProfileActivity> rule = new ActivityTestRule<>(MockProfileActivity.class, true, true);
    /**
    * Runs before all tests and creates solo instance.
            * @throws Exception
    */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Gets the Activity
    * @throws Exception
    */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
        solo.assertCurrentActivity("Wrong Activity", MockProfileActivity.class);

    }







}
