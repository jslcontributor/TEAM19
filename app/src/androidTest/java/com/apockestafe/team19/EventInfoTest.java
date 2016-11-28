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
public class EventInfoTest {
    @Rule
    public ActivityTestRule<EventInfo> mActivityRule = new ActivityTestRule(EventInfo.class, true, false);


    @Test
    public void ChangeviewWithBackToEvents() {
        Intent intent = new Intent();
        intent.putExtra("eventNumber", "0");
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.settingsButton)).check(matches(isDisplayed()));

    }
   @Test
    public void ChangeviewWithviewItems() {
       Intent intent = new Intent();
       intent.putExtra("eventNumber", "0");
       mActivityRule.launchActivity(intent);
       onView(withId(R.id.itemsButton)).perform(click());
       onView(withId(R.id.addItemButton)).check(matches(isDisplayed()));
    }
    @Test
    public void ChangeviewWithGoToMap() {
        Intent intent = new Intent();
        intent.putExtra("eventNumber", "0");
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.MapButton)).perform(click());
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));

    }
    @Test
    public void ChangeviewWithGoToListRide() {
        Intent intent = new Intent();
        intent.putExtra("eventNumber", "0");
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.listRideButton)).perform(click());
        onView(withId(R.id.submitRide)).check(matches(isDisplayed()));

    }
}
