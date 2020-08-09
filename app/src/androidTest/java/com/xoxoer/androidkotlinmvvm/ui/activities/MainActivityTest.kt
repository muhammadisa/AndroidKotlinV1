package com.xoxoer.androidkotlinmvvm.ui.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.xoxoer.androidkotlinmvvm.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_activityOnView() {

        onView(withId(R.id.main))
            .check(matches(isDisplayed()))

        onView(withId(R.id.main))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    }

    @Test
    fun test_textViewExampleOnView() {

        onView(withId(R.id.text_view_example))
            .check(matches(isDisplayed()))

        onView(withId(R.id.text_view_example))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    }

    @Test
    fun test_buttonRefreshOnView() {

        onView(withId(R.id.button_refresh))
            .check(matches(isDisplayed()))

        onView(withId(R.id.button_refresh))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

    }
}