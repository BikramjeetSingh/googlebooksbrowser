package com.example.robby.googlebooksbrowser;

import android.support.test.rule.ActivityTestRule;

import com.example.robby.googlebooksbrowser.activities.MainScreen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainScreenTest {

    @Rule
    public ActivityTestRule<MainScreen> activityTestRule = new ActivityTestRule<>(MainScreen.class);

    @Before
    public void setUp() {
        activityTestRule.getActivity();
    }

    @Test
    public void editText_initially_empty() {
        onView(withId(R.id.et_book)).check(matches(withText("")));
    }
}
