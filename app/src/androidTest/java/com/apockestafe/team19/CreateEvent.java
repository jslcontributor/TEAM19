package com.apockestafe.team19;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;


/**
 * Created by nsallaire on 11/28/16.
 */
@RunWith(AndroidJUnit4.class)
public class CreateEvent {
    @Rule
    public ActivityTestRule<EventActivity> mActivityRule = new ActivityTestRule(EventActivity.class);

    @Test
    public void testCreateEventButton() {
        onView(withId(R.id.button_create)).perform(click());
        onView(withId(R.id.settingsButton)).check(matches(isDisplayed()));
        onView(withId(R.id.button1)).check(matches(isDisplayed()));

    }

    @Test
    public void testCancelButton() {

        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.settingsButton)).check(matches(isDisplayed()));
        onView(withId(R.id.button1)).check(matches(isDisplayed()));

    }

    @Test
    public void EventPushedToDatabaseTest() {
        final String title = "Test Event";
        final String date = "12/1/16";
        final String time = "12:00";
        final String location = "9500 Gilman Dr, La Jolla, CA, 92093";
        final String description = "This is a test event.";
        List<RideInfo> rideInfo = new ArrayList<>();
        List<String> peopleTest = new ArrayList<>();
        ArrayList<String> itemTest = new ArrayList<>();
        ArrayList<String> attendingListTest = new ArrayList<>();
        final DatabaseReference ref;
        final FirebaseDatabase database;

        peopleTest.add("Test ride attendee");
        RideInfo testRide = new RideInfo("9500 Gilman Dr, La Jolla, CA, 92093", peopleTest, 3);
        rideInfo.add(testRide);
        itemTest.add("Test item");
        attendingListTest.add("Test attendee");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("TEAM19");

        final Event event = new Event(title, date, time, location, description, rideInfo, itemTest, attendingListTest);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String count = "0";
                ref.child("events").child(count).setValue(event);

                String eventTitle = (String) dataSnapshot.child("events").child(count).child("title").getValue();
                String eventDate = (String) dataSnapshot.child("events").child(count).child("date").getValue();
                String eventTime = (String) dataSnapshot.child("events").child(count).child("time").getValue();
                String eventDescription = (String) dataSnapshot.child("events").child(count).child("description").getValue();
                String eventLocation = (String) dataSnapshot.child("events").child(count).child("location").getValue();

                if ((eventTitle.equals(title) && (eventDate.equals(date) && (eventTime.equals(time) && eventLocation.equals(location) && eventDescription.equals(description))))) {
                    assert(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}
