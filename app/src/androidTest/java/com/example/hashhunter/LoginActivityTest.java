package com.example.hashhunter;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoginActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class, true, true);

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
    /**
     * Checks the register user activity
     */
    public void checkRegister() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnButton("Register");
        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.username_edit_text), "RegisterTest");
        solo.enterText((EditText) solo.getView(R.id.email_edit_text), "TestEmail@gmail.com");
        solo.clickOnButton("Submit");
        solo.assertCurrentActivity("Wrong Activity", DashboardActivity.class);
    }

    @Test
    /**
     * Checks that the login button takes the user to the scanner
     */
    public void checkLoginButton() {
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnButton("Login using a QR Code");
        solo.assertCurrentActivity("Wrong Activity", ScanActivity.class);
    }

}
