package com.example.hashhunter;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SearchActivityTest {
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
     * Tests to see if when clicked on a username it directs to their profile
     * firsts goes through explore activity to load search activity with data otherwise nothing is clickable
     * @throws Exception
     */
    @Test
    public void checkSwitchToProfile() throws Exception {

        //send to search activity with data
        solo.assertCurrentActivity("Wrong Activity", ExploreActivity.class);
        solo.getView(R.id.username_search); //Select search bar
        solo.enterText(0, "Aaron");
        solo.pressSoftKeyboardSearchButton();
        solo.waitForActivity(SearchActivity.class);
        solo.assertCurrentActivity("Wrong Activity", SearchActivity.class);


        solo.clickOnView(solo.getView(R.id.search_username)); //Select username

        solo.waitForActivity(ProfileActivity.class);
        solo.assertCurrentActivity("Wrong Activity", ProfileActivity.class);
    }
}
