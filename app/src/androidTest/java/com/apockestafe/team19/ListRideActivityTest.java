package com.apockestafe.team19;

/**
 * Created by nsallaire on 11/28/16.
 */

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
public class ListRideActivityTest {
    @Rule
    public ActivityTestRule<ListRideActivity> mActivityRule = new ActivityTestRule(ListRideActivity.class, true, false);


//    @Test
//    public void ChangeviewWithSubmitRide() {
//        Intent intent = new Intent();
//        intent.putExtra("eventNumber", "0");
//        mActivityRule.launchActivity(intent);
//        onView(withId(R.id.submitRide)).perform(click());
//        onView(withId(R.id.listRideButton)).check(matches(isDisplayed()));
//
//    }

    @Test
    public void ChangeviewWithBackToEventView() {
        Intent intent = new Intent();
        intent.putExtra("eventNumber", "0");
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.cancelRide)).perform(click());
        onView(withId(R.id.listRideButton)).check(matches(isDisplayed()));

    }
}
