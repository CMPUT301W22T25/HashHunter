package com.example.hashhunter;

import android.app.Activity;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DeletePlayerActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<DeletePlayerActivity> rule =
            new ActivityTestRule<>(DeletePlayerActivity.class, true, true);

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
     * Checks if entering info and submitting it works in the delete player activity
     */
    @Test
    public void checkSubmit() {
        solo.assertCurrentActivity("Wrong Activity", DeletePlayerActivity.class);
        solo.enterText((EditText) solo.getView(R.id.del_username_edit_text), "randomUsernameThatDoesntExist");
        solo.clickOnButton("Submit");
        solo.waitForText("no username found");
    }
}

