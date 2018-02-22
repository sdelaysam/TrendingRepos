package test.trendingrepos.tests

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import test.trendingrepos.MainActivity
import test.trendingrepos.R
import test.trendingrepos.utils.ViewMatchers

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

@RunWith(AndroidJUnit4::class)
class StartupTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, true, true)

    @Test
    fun showActivity() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(ViewMatchers.toolbarTitle())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.app_name)))
    }
}