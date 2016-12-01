package com.apockestafe.team19;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by nsallaire on 11/28/16.
 */
@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {
    @Rule
    public ActivityTestRule<SetiingsActivity> mActivityRule = new ActivityTestRule(SetiingsActivity.class, true, false);


    @Test
    public void testBackButtonToMainActivity() {
        Intent intent = new Intent();
        intent.putExtra("eventNumber", "0");
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.settingsButton)).check(matches(isDisplayed()));

    }

}