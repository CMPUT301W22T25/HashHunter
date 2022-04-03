package com.example.hashhunter;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DeleteGameCodeActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<DeleteGameCodeActivity> rule =
            new ActivityTestRule<>(DeleteGameCodeActivity.class, true, true);

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

    @Test
    public void checkDelete() {
//        solo.waitForActivity(DeletePlayerActivity.class);
//        solo.assertCurrentActivity("Wrong Activity", DeleteGameCodeActivity.class);
        solo.assertCurrentActivity("Wrong Activity 2", DeleteGameCodeActivity.class);
//        solo.assertCurrentActivity("Wrong Activity 1", ScanActivity.class);
//        solo.finishOpenedActivities();
//        solo.assertCurrentActivity("Wrong Activity 2", DeleteGameCodeActivity.class);
    }
}
