package test.trendingrepos.utils

import android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom
import android.support.test.espresso.matcher.ViewMatchers.withParent
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

object ViewMatchers {

    fun toolbarIconIcon(): Matcher<View> {
        return allOf<View>(
                isAssignableFrom(ImageButton::class.java),
                withParent(isAssignableFrom(Toolbar::class.java)))
    }

    fun toolbarTitle(): Matcher<View> {
        return allOf<View>(
                isAssignableFrom(TextView::class.java),
                withParent(isAssignableFrom(Toolbar::class.java)))
    }

}

