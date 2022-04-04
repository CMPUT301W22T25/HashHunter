package com.example.hashhunter;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DeleteGameCodeActivityTest {
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

    @Test
    public void checkDelete() {
        // assumes the user will scan a code
        solo.assertCurrentActivity("Wrong Activity", OwnerActivity.class);
        solo.clickOnView(solo.getView(R.id.delete_code_button)); //Select Delete Code Button
        solo.waitForActivity(ScanActivity.class);
        solo.assertCurrentActivity("Wrong Activity", ScanActivity.class);
        solo.waitForActivity(DeleteGameCodeActivity.class);
        solo.assertCurrentActivity("Wrong Activity", DeleteGameCodeActivity.class);
        solo.waitForText("Latitude: ");
    }
}
