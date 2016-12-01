package com.apockestafe.team19;

/**
 * Created by nsallaire on 11/28/16.
 */


import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

/**
 * Created by nsallaire on 11/28/16.
 */
@RunWith(AndroidJUnit4.class)
public class ItemsActivityTest {
    @Rule
    public ActivityTestRule<ItemsActivity> mActivityRule = new ActivityTestRule(ItemsActivity.class, true, false);


    @Test
    public void testBackButton() {
        Intent intent = new Intent();
        intent.putExtra("eventNumber", "0");
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.itemsButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testNoTextAddItemButton() {
        Intent intent = new Intent();
        intent.putExtra("eventNumber", "0");
        mActivityRule.launchActivity(intent);
        onView(withId(R.id.addItemButton)).perform(click());
        String s = (String) mActivityRule.getActivity().addItemButton.getText();
        if (s.equals(null)) {
            onView(withId(R.id.addItemButton)).check(matches(isDisplayed()));
        }
    }
}
