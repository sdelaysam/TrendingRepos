package test.trendingrepos.tests

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.whenever
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import test.trendingrepos.R
import test.trendingrepos.common.di.TestApiModule
import test.trendingrepos.main.MainActivity
import test.trendingrepos.stubs.getEmptyRepositoriesList
import test.trendingrepos.stubs.getRepositoriesList
import test.trendingrepos.stubs.getRepositoriesListError
import test.trendingrepos.utils.RecyclerViewMatcher.hasItemsCount
import test.trendingrepos.utils.RecyclerViewMatcher.withRecyclerView
import test.trendingrepos.utils.ViewMatchers

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java, true, false)

    @After
    fun teardown() {
        reset(TestApiModule.githubApi)
    }

    @Test
    fun checkList() {
        doReturn(getRepositoriesList()).whenever(TestApiModule.githubApi).getRepositories(any(), any(), any())
        activityRule.launchActivity(Intent())

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(ViewMatchers.toolbarTitle())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.app_name)))

        onView(withId(R.id.recycler_view))
                .check(matches(isDisplayed()))
                .check(matches(hasItemsCount(3)))

        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(matches(hasDescendant(withText("name1"))))
                .check(matches(hasDescendant(withText("description1"))))

        onView(withRecyclerView(R.id.recycler_view).atPosition(1))
                .check(matches(hasDescendant(withText("name2"))))
                .check(matches(hasDescendant(withText("description2"))))

        onView(withRecyclerView(R.id.recycler_view).atPosition(2))
                .check(matches(hasDescendant(withText("name3"))))
                .check(matches(hasDescendant(withText("description3"))))

        onView(withId(R.id.empty_text)).check(matches(not(isDisplayed())))
    }

    @Test
    fun checkEmptyList() {
        doReturn(getEmptyRepositoriesList()).whenever(TestApiModule.githubApi).getRepositories(any(), any(), any())
        activityRule.launchActivity(Intent())

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(ViewMatchers.toolbarTitle())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.app_name)))

        onView(withId(R.id.recycler_view))
                .check(matches(isDisplayed()))
                .check(matches(hasItemsCount(0)))

        onView(withId(R.id.empty_text))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.no_repos)))
    }

    @Test
    fun checkListFailed() {
        val message = "No network"
        doReturn(getRepositoriesListError(message)).whenever(TestApiModule.githubApi).getRepositories(any(), any(), any())
        activityRule.launchActivity(Intent())

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(ViewMatchers.toolbarTitle())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.app_name)))

        onView(withId(R.id.recycler_view))
                .check(matches(isDisplayed()))
                .check(matches(hasItemsCount(0)))

        onView(withId(R.id.empty_text))
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.repos_failed)))

        onView(ViewMatchers.snackbar(message))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun checkNavigation() {
        doReturn(getRepositoriesList()).whenever(TestApiModule.githubApi).getRepositories(any(), any(), any())
        activityRule.launchActivity(Intent())

        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(ViewMatchers.toolbarTitle())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.app_name)))

        onView(withId(R.id.recycler_view))
                .check(matches(isDisplayed()))
                .check(matches(hasItemsCount(3)))

        onView(withRecyclerView(R.id.recycler_view).atPosition(0))
                .check(matches(hasDescendant(withText("name1"))))
                .check(matches(hasDescendant(withText("description1"))))
                .perform(click())

        onView(ViewMatchers.toolbarTitle())
                .check(matches(isDisplayed()))
                .check(matches(withText("name1")))

        onView(withId(R.id.title_text))
                .check(matches(isDisplayed()))
                .check(matches(withText("fullName1")))

        onView(withId(R.id.description_text))
                .check(matches(isDisplayed()))
                .check(matches(withText("description1")))

        onView(ViewMatchers.toolbarIcon())
                .check(matches(isDisplayed()))
                .perform(click())

        onView(ViewMatchers.toolbarTitle())
                .check(matches(isDisplayed()))
                .check(matches(withText(R.string.app_name)))

        onView(withRecyclerView(R.id.recycler_view).atPosition(1))
                .check(matches(hasDescendant(withText("name2"))))
                .check(matches(hasDescendant(withText("description2"))))
                .perform(click())

        onView(withId(R.id.title_text))
                .check(matches(isDisplayed()))
                .check(matches(withText("fullName2")))

        onView(withId(R.id.description_text))
                .check(matches(isDisplayed()))
                .check(matches(withText("description2")))

    }
}