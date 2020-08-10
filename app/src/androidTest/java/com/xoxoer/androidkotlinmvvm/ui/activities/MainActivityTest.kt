package com.xoxoer.androidkotlinmvvm.ui.activities

import android.content.Context
import android.net.wifi.WifiManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.google.common.truth.Truth.assertThat
import com.xoxoer.androidkotlinmvvm.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
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

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_verifyInjection() {
        ActivityScenario.launch(MainActivity::class.java).use {
            it.moveToState(Lifecycle.State.STARTED)
            it.onActivity { activity ->
                assertThat(activity.exampleViewModel).isNotNull()
                activity.exampleViewModel.fetchExample()
                activity.exampleViewModel.exampleSuccess.observe(activity, Observer { example ->
                    assertThat(example).isNotNull()
                })
            }
        }
    }

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

    @Test
    fun test_refreshButtonClick() {

        onView(withId(R.id.button_refresh))
            .check(matches(isDisplayed()))

        onView(withId(R.id.button_refresh))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.button_refresh))
            .check(matches(isDisplayed()))

        onView(withId(R.id.button_refresh)).perform(click())

        onView(withId(R.id.text_view_example))
            .check(matches(isDisplayed()))

        onView(withId(R.id.text_view_example))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))

        onView(withId(R.id.text_view_example)).check(matches(withText("laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium")))

    }

}