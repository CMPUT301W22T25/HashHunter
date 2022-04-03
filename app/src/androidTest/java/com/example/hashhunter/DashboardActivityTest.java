package com.example.hashhunter;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for DashboardActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class DashboardActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<DashboardActivity> rule =
            new ActivityTestRule<>(DashboardActivity.class, true, true);

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
     * Checks clicking of dashboard buttons and going to each separate activity
     */
    @Test
    public void checkSwitchToMain() {
        solo.assertCurrentActivity("Wrong Activity", DashboardActivity.class);
        solo.clickOnView(solo.getView(R.id.home)); //Select Home Button
        solo.waitForActivity(DashboardActivity.class);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }
    @Test
    public void checkSwitchToExplore() {
        solo.assertCurrentActivity("Wrong Activity", DashboardActivity.class);
        solo.clickOnView(solo.getView(R.id.explore)); //Select Explore Button
        solo.waitForActivity(DashboardActivity.class);
        solo.assertCurrentActivity("Wrong Activity", ExploreActivity.class);
    }
    @Test
    public void checkSwitchToMap() {
        solo.assertCurrentActivity("Wrong Activity", DashboardActivity.class);
        solo.clickOnView(solo.getView(R.id.map)); //Select Map Button
        solo.waitForActivity(DashboardActivity.class);
        solo.assertCurrentActivity("Wrong Activity", MapActivity.class);
    }
    @Test
    public void checkSwitchToScan() {
        solo.assertCurrentActivity("Wrong Activity", DashboardActivity.class);
        solo.clickOnView(solo.getView(R.id.scan)); //Select Scan Button
        solo.waitForActivity(DashboardActivity.class);
        solo.assertCurrentActivity("Wrong Activity", ScanActivity.class);
    }

    @Test
    public void checkSwitchToProfile() {
        solo.assertCurrentActivity("Wrong Activity", DashboardActivity.class);
        solo.clickOnView(solo.getView(R.id.profile)); //Select Profile Button
        solo.waitForActivity(DashboardActivity.class);
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }
    /**
     * Checks clicking of dashboard buttons and going to each separate activity
     */
    @Test
    public void checkHomeFragmentText() {
        assert(solo.searchText("Your forest could use some work! \nKeep on hunting!"));
        assert(solo.searchText("Wow you are quite the hunter! \nGreat job!"));
        assert(solo.searchText("Nice forest! \nKeep it growing!"));
        assert(solo.searchText("Amazing work! \nYou are truly a top hunter!"));
    }



}
