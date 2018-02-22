package test.trendingrepos.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import test.trendingrepos.R
import test.trendingrepos.databinding.ActivityMainBinding
import test.trendingrepos.details.DetailsFragment
import test.trendingrepos.repos.ReposFragment
import javax.inject.Inject

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { goBack() }

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.title.observe(this, Observer { title -> doOnTitle(title) })
        viewModel.segue.observe(this, Observer { segue -> doOnSegue(segue) })
        viewModel.backEnabled.observe(this, Observer { showBack -> doOnBackEnabled(showBack) })

        doOnSegue(Segue(SegueType.SHOW_REPOS))
    }

    private fun doOnTitle(title: String?) {
        if (title != null) {
            supportActionBar?.title = title
        } else {
            supportActionBar?.title = getString(R.string.app_name)
        }
    }

    private fun doOnBackEnabled(enabled: Boolean?) {
        enabled?.let {
            supportActionBar?.setDisplayHomeAsUpEnabled(it)
            supportActionBar?.setDisplayShowHomeEnabled(it)
        }
    }

    private fun doOnSegue(segue: Segue?) {
        segue?.let {
            if (it.processed)
                return

            when (it.type) {
                SegueType.SHOW_REPOS -> showReposFragment()
                SegueType.OPEN_DETAILS -> showDetailsFragment()
                SegueType.GO_BACK -> goBack()
            }
            it.processed = true
        }
    }

    private fun showReposFragment() {
        var fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, ReposFragment())
                    .addToBackStack(null)
                    .commit()
        }
    }

    private fun showDetailsFragment() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, DetailsFragment())
                .addToBackStack(null)
                .commit()
    }

    private fun goBack() {
        onBackPressed()
    }


}