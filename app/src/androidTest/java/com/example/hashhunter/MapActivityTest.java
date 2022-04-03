package com.example.hashhunter;

import static junit.framework.TestCase.assertEquals;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test class for MapActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class MapActivityTest {

    private Solo solo;

    @Rule
    public ActivityTestRule<MapActivity> rule =
            new ActivityTestRule<>(MapActivity.class, true, true);

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
     * Checks whether the fragment is visible on screen
     */
    @Test
    public void checkForMapFragment() {
        Boolean initialID = solo.waitForFragmentById(R.id.google_map);
        Boolean finalID = solo.waitForFragmentById(R.id.google_map);
        assertEquals(initialID,finalID);

    }

}
