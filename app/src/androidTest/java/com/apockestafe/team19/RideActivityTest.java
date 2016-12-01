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
public class RideActivityTest {
    @Rule
    public ActivityTestRule<Ride> mActivityRule = new ActivityTestRule(Ride.class, true, false);


    @Test
    public void testBackButtonToMaps() {
        Intent intent = new Intent();
        intent.putExtra("Marker Name", "3853 Van Dyke Ave, San Diego, CA, 92105:0");
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));

    }

    @Test
    public void testAddToCarButton() {
        Intent intent = new Intent();
        intent.putExtra("Marker Name", "3853 Van Dyke Ave, San Diego, CA, 92105:0");
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.addToCarButton)).perform(click());
        onView(withId(R.id.addToCarButton)).check(matches(isDisplayed()));

    }

    @Test
    public void ChangeviewDataWithRemoveClick() {
        Intent intent = new Intent();
        intent.putExtra("Marker Name", "3853 Van Dyke Ave, San Diego, CA, 92105:0");
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.removeFromCarButton)).perform(click());
        onView(withId(R.id.removeFromCarButton)).check(matches(isDisplayed()));

    }

}