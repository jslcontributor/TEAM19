package com.apockestafe.team19;


        import android.app.Instrumentation;
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
public class CreateEvent {
    @Rule
    public ActivityTestRule<EventActivity> mActivityRule = new ActivityTestRule(EventActivity.class);


    @Test
    public void ChangeviewWithCreateEvent() {
        onView(withId(R.id.button_create)).perform(click());
        onView(withId(R.id.settingsButton)).check(matches(isDisplayed()));
        onView(withId(R.id.button1)).check(matches(isDisplayed()));

    }

    public ActivityTestRule<EventActivity> mActivityRule1 = new ActivityTestRule(EventActivity.class);

    @Test
    public void ChangeviewWithCreateEventCancel() {

        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.settingsButton)).check(matches(isDisplayed()));
        onView(withId(R.id.button1)).check(matches(isDisplayed()));

    }
}
