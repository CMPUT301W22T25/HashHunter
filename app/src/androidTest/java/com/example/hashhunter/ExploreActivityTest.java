package com.example.hashhunter;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class ExploreActivityTest {

    private Solo solo;


    @Rule
    public ActivityTestRule<ExploreActivity> rule = new ActivityTestRule<>(ExploreActivity.class, true, true);
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
    }


    /**
     * Tests to see if the search bar properly sends to search activity
     * @throws Exception
     */
    @Test
    public void checkSearchUsername() throws Exception {
        solo.assertCurrentActivity("Wrong Activity", ExploreActivity.class);
        solo.getView(R.id.username_search); //Select search bar
        solo.enterText(0, "username");
        solo.pressSoftKeyboardSearchButton();

        solo.waitForActivity(SearchActivity.class);
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);
    }

    /**
     * Tests to see if clicking on the scan by QR button directs to the proper
     * activity
     * @throws Exception
     */
    @Test
    public void checkScanQR() throws Exception {
        solo.assertCurrentActivity("Wrong Activity", ExploreActivity.class);
        solo.clickOnView(solo.getView(R.id.scan_profile_button)); //Select Scan by QR Button

        solo.waitForActivity(ScanActivity.class);
        solo.assertCurrentActivity("Wrong Activity", ScanActivity.class);
    }

    /**
     * Checks if scanning profile code takes you to ProfileActivity. Need camera and code to scan to test.
     */
    @Test
    public void checkProfileQR(){
        solo.assertCurrentActivity("Wrong Activity", ExploreActivity.class);
        solo.clickOnView(solo.getView(R.id.scan_profile_button)); //Select Scan by QR Button
        solo.waitForActivity(ScanActivity.class);
        solo.assertCurrentActivity("Wrong Activity", ScanActivity.class);
        solo.waitForActivity(ProfileActivity.class);
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }



}
