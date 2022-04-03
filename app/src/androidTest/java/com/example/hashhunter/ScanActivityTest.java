package com.example.hashhunter;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for ScanActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class ScanActivityTest {

    private Solo solo;


    @Rule
    public ActivityTestRule<ScanActivity> rule =
            new ActivityTestRule<>(ScanActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * Checks if continue takes you to ScanSubmitActivity. Need camera and code to scan to test.
     */
    @Test
    public void checkSubmit(){
        solo.assertCurrentActivity("Wrong Activity", ScanActivity.class);
        solo.clickOnView(solo.getView(R.id.continue_button)); //Select Continue Button
        solo.waitForActivity(ScanSubmitActivity.class);
        solo.assertCurrentActivity("Wrong Activity", ScanSubmitActivity.class);
    }

}
