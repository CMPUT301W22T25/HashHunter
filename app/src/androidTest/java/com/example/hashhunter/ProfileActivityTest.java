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
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
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
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;





public class ProfileActivityTest {
    /**
     * This class heavily relies on being the ProfileActivityTest account. Please before executing it
     * save your old profile qr code somewhere because you will not get it back. I found it really hard
     * (Mainly because robotium sucks and the way the activity works) to do completely isolated tests
     * so I decided to combine both qr visualizer and profile activity testing as one relies on the other
     * Also make sure you are already registered.
     *
     *
     *This was also really hard because of all the initial set up it requires to do (And robotium sucks) so please be merciful.
     */
    private Solo solo;
     SharedPreferences preferences;
     String  OldId;
     String  OldUserName;
    private  FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String TestUserId ="d9169874-42b2-4fe1-961a-2083f7515e98";
    private String TestUserName ="ProfileActivityTest";

    /**
     *
     * @param username - A username to create a new player
     * @return A player id
     */
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

    /**
     *
     * @param title - A gamecode title
     * @param code - A code to put in the database
     * @param uniqueId - A user id
     * @param points - Amount of points
     * @return - Returns the gamecode code
     */
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
     * This sets the shared preferences to our desired user (In this case ProfileTestActivity)
     * @param id A user id to set the shared preferences to
     * @param username A username
     * @param act The current activity being run
     * */
    public void setSharedPrefs(String id, String username, Activity act) {
            try {
                ProfileActivity p = (ProfileActivity) act;
                preferences = p.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                //Store old id
                OldId = preferences.getString(PREF_UNIQUE_ID, "");
                OldUserName = preferences.getString(PREF_USERNAME, "");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(PREF_UNIQUE_ID, id);
                editor.putString(PREF_USERNAME, username);
                editor.commit();
                editor.apply();
                p.recreate();
            }
            catch(Exception e){
                    System.out.println(e);
                }
    }
    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{

        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
        solo.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setSharedPrefs(TestUserId, TestUserName, solo.getCurrentActivity());
            }
        });

        solo.sleep(3000);
    }


    /**
     * Gets the Activity
    * @throws Exception
    */
    @Test
    public void start(){
        Activity activity = rule.getActivity();
    }

    /**
     * Tests if the username coincides with the assigned username in shared prefs
     */

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
    /**
     * Tests if the email coincides with the email obtained from loading the profile
     */
    @Test
    public void TestEmail(){
        String email = "dontDelete@gmail.com";
        ProfileActivity p= rule.getActivity();
        assertEquals(email, p.emailView.getText());
    }

    /**
     * Tests the action of clicking on another qr
     */
    @Test
    public void TestGoingOnQr(){
        String gameId = FakeAddGameCode("TestProfile", "wqe2eqw12", TestUserId, 10);
        RecyclerView recycler = (RecyclerView) solo.getView(R.id.treeList);
        QRAdapter adapter = (QRAdapter) recycler.getAdapter();
        System.out.println(adapter);
        solo.clickOnView(recycler.getChildAt(0));
        solo.assertCurrentActivity("QR Visualizer", QRVisualizerActivity.class);
        GameCodeController controller = adapter.getItem(0);

    }

    /**
     * Tests the action of deleting a gamecode and checks whether if the list updates
     */
    @Test
    public void TestDeletingGameCode(){
        //Wait until recycler loads
        solo.waitForView(R.id.treeList);
        //Get activity
        ProfileActivity p= rule.getActivity();
        // String gameId = FakeAddGameCode("TestProfile", "wqe2eqw12", TestUserId, 1);
        RecyclerView recycler = (RecyclerView) solo.getView(R.id.treeList);
        //Wait for stuff to load
        solo.sleep(5000);
        //Set qr adapter
        QRAdapter adapter = p.QRRecycleAdapter;
        //Get the size of this adapter
        int oldSize = adapter.getItemCount();
        //Click on the first item (We assume is named Test Profile
        solo.clickOnView(recycler.getChildAt(0));
        //Check if we are on activity
        solo.assertCurrentActivity("QR Visualizer", QRVisualizerActivity.class);

        //Wait for stuff to load
        solo.sleep(5000);
        //Click on the title (For some reason long clicking on the view did not work)
        solo.clickLongOnText("TestProfile");
        //Click on delete
        solo.clickOnView(solo.getView(R.id.delete_visualizer_button));
        //Wait for stuff to load
        solo.sleep(15000);
        //Get an updated version of the profile and adapter
        ProfileActivity pUpdated = (ProfileActivity) solo.getCurrentActivity();
        QRAdapter newAdapter = pUpdated.QRRecycleAdapter;
        //Check if there are less items
        assertEquals(oldSize-1, newAdapter.getItemCount());


    }

    /** Tests commenting on a gamecode, tests if the contents on the list are the same
     * and if the username coincides with the person who commented.
     */
    @Test
    public void testCommentingOnGameCode(){
        //Wait until recycler loads
        solo.waitForView(R.id.treeList);
        //Get activity
        ProfileActivity p= rule.getActivity();
        // String gameId = FakeAddGameCode("TestProfile", "wqe2eqw12", TestUserId, 1);
        RecyclerView recycler = (RecyclerView) solo.getView(R.id.treeList);
        //Wait for stuff to load
        solo.sleep(5000);
        //Set qr adapter
        QRAdapter adapter = p.QRRecycleAdapter;
        //Get the size of this adapter
        //Click on the first item (We assume is named Test Profile
        solo.clickOnView(recycler.getChildAt(0));
        //Check if we are on activity
        solo.assertCurrentActivity("QR Visualizer", QRVisualizerActivity.class);
        EditText textBox = (EditText) solo.getView(R.id.textBox);
        String textToEnter = "HAHAHAHAHA THIS QR SUCKS!";
        solo.enterText(textBox, textToEnter);
        solo.sleep(5000);
        QRVisualizerActivity OldQRVis = (QRVisualizerActivity) solo.getCurrentActivity();
        int oldSize = OldQRVis.commentAdapter.getItemCount();
        solo.sleep(5000);
        solo.clickOnView(solo.getView(R.id.sendButton));
        solo.sleep(5000);
        QRVisualizerActivity QRVis = (QRVisualizerActivity) solo.getCurrentActivity();
        int newSize = QRVis.commentAdapter.getItemCount();
        solo.sleep(5000);
        assertEquals(oldSize+1, newSize);
        CommentController comment = QRVis.commentAdapter.getItem(newSize-1);
        String username = comment.getOwner();
        String content = comment.getComment();
        assertEquals(textToEnter, content);
        assertEquals(TestUserName, username);


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
                            if (points > player.getMaxGameCodePoints()) {
                                document.getReference().update("maxGameCodePoints", points);
                            }
                        } else {
                            Log.d("Get player", "get failed with ", task.getException());
                        }
                    }
                });

    }

    @After
    public  void RestoreUser(){

        solo.getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setSharedPrefs(OldId, OldUserName, solo.getCurrentActivity());
            }
        });

    }


}
