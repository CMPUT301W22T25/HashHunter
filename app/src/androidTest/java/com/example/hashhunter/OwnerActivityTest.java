package com.example.hashhunter;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class OwnerActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<OwnerActivity> rule =
            new ActivityTestRule<>(OwnerActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
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
     * Checks if the delete player button works in the owner activity
     */
    @Test
    public void checkDeletePlayerButton() {
        solo.assertCurrentActivity("Wrong Activity", OwnerActivity.class);
        solo.clickOnButton("Delete Player");
        solo.assertCurrentActivity("Wrong Activity", DeletePlayerActivity.class);
    }

    /**
     * Checks if the delete code button works in the owner activity
     */
    @Test
    public void checkDeleteCodeButton() {
        solo.assertCurrentActivity("Wrong Activity", OwnerActivity.class);
        solo.clickOnButton("Delete Code");
        solo.assertCurrentActivity("Wrong Activity", DeleteGameCodeActivity.class);
    }


}
