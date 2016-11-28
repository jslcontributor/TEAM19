package com.apockestafe.team19;

import android.support.test.filters.LargeTest;
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
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void ChangeviewWithAddEvent() {
        onView(withId(R.id.button1)).perform(click());
        onView(withId(R.id.title)).check(matches(isDisplayed()));
    }
    @Test
    public void LaunchScreen() {
        onView(withId(R.id.button1)).perform(click());
        onView(withId(R.id.title)).check(matches(isDisplayed()));
    }
    @Test
    public void ChangeviewWithSettingsButton() {
        onView(withId(R.id.settingsButton)).perform(click());
        onView(withId(R.id.firstNameEditText)).check(matches(isDisplayed()));
    }
}
