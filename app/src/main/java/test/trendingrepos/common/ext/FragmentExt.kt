package test.trendingrepos.common.ext

import android.arch.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import test.trendingrepos.main.MainViewModel
import test.trendingrepos.main.Segue

/**
 * Router would fit better here.
 * This implementation has several issues, so its just for the sake of demo.
 * And nice Kotlin extensions feature :)
 *
 * Created on 22/02/2018
 * @author sdelaysam
 */

fun DaggerFragment.setTitle(title: String?) {
    activity?.let {
        val viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        viewModel.title.value = title
    }
}

fun DaggerFragment.setBackEnabled(enabled: Boolean?) {
    activity?.let {
        val viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        viewModel.backEnabled.value = enabled
    }
}

fun DaggerFragment.performSegue(segue: Segue?) {
    activity?.let {
        val viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        viewModel.segue.value = segue
    }
}