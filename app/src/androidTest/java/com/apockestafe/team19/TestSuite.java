package com.apockestafe.team19;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by JLee on 11/30/16.
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        CreateEvent.class,
        EventInfoTest.class,
        ItemsActivityTest.class,
        ListRideActivityTest.class,
        MainActivityTest.class,
        MapsActivityTest.class,
        RideActivityTest.class,
        SettingsActivityTest.class
})

public class TestSuite {
}
