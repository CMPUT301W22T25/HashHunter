package com.example.hashhunter;

import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

import org.junit.Rule;

public class LoginActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);


}
