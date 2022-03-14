package com.example.hashhunter;

import static org.junit.Assert.assertTrue;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for ScanSubmitActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class ScanSubmitActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<ScanSubmitActivity> rule =
            new ActivityTestRule<>(ScanSubmitActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void checkSwitchToDashboard() {
        solo.assertCurrentActivity("Wrong Activity", ScanSubmitActivity.class);
        solo.clickOnView(solo.getView(R.id.save_button)); //Select Save Button
        solo.waitForActivity(DashboardActivity.class);
        solo.assertCurrentActivity("Wrong Activity", DashboardActivity.class);
    }

    @Test
    public void checkAddLocation(){
        solo.assertCurrentActivity("Wrong Activity", ScanSubmitActivity.class);
        solo.clickOnView(solo.getView(R.id.add_location_button)); //Select Add Location Button
        assertTrue(solo.waitForText("Latitude", 1, 10000));
    }
}
