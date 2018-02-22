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
import io.reactivex.Single
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import test.trendingrepos.main.MainActivity
import test.trendingrepos.R
import test.trendingrepos.common.api.GithubDto
import test.trendingrepos.common.di.TestApiModule
import test.trendingrepos.utils.RecyclerViewMatcher.hasItemsCount
import test.trendingrepos.utils.RecyclerViewMatcher.withRecyclerView
import test.trendingrepos.utils.ViewMatchers
import java.util.*
import kotlin.collections.ArrayList

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

    private fun getEmptyRepositoriesList() = Single.defer {
        Single.just(GithubDto.ListResponse<GithubDto.Repository>(0, true, Collections.emptyList()))
    }

    private fun getRepositoriesList() = Single.defer {
        val list = ArrayList<GithubDto.Repository>()
        list.add(GithubDto.Repository(1, "name1", "fullName1", getOwner(), false, "", "description1", false, "", Date(), Date(), Date(), "", 100, 1, 1, "", 1, 1, "", "", 10.0))
        list.add(GithubDto.Repository(2, "name2", "fullName2", getOwner(), false, "", "description2", false, "", Date(), Date(), Date(), "", 200, 2, 2, "", 2, 2, "", "", 20.0))
        list.add(GithubDto.Repository(3, "name3", "fullName3", getOwner(), false, "", "description3", false, "", Date(), Date(), Date(), "", 300, 3, 3, "", 3, 3, "", "", 30.0))
        Single.just(GithubDto.ListResponse(list.size, true, list))
    }

    private fun getRepositoriesListError(message: String) = Single.defer {
        Single.error<GithubDto.ListResponse<GithubDto.Repository>>(Throwable(message))
    }

    private fun getOwner() = GithubDto.User("owner", 1L, "", "");
}