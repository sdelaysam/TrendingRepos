package test.trendingrepos.utils

import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import org.hamcrest.Matchers.allOf

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

object ViewMatchers {

    fun toolbarIcon() = allOf<View>(
                isAssignableFrom(ImageButton::class.java),
                withParent(isAssignableFrom(Toolbar::class.java)))

    fun toolbarTitle() = allOf<View>(
                isAssignableFrom(TextView::class.java),
                withParent(isAssignableFrom(Toolbar::class.java)))

    fun snackbar(message: String) = allOf<View>(
            withId(android.support.design.R.id.snackbar_text),
            withText(message))
}

