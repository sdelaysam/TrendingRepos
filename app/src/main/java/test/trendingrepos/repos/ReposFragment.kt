package test.trendingrepos.repos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import test.trendingrepos.R
import test.trendingrepos.common.ext.performSegue
import test.trendingrepos.common.ext.setTitle
import test.trendingrepos.common.ext.setBackEnabled
import test.trendingrepos.databinding.FragmentReposBinding
import javax.inject.Inject

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

class ReposFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ReposViewModel

    private lateinit var binding: FragmentReposBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ReposViewModel::class.java)
        viewModel.segue.observe(this, Observer { segue -> performSegue(segue) })
        viewModel.error.observe(this, Observer { message -> showError(message) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repos, container, false)
        binding.viewModel = viewModel
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = viewModel.adapter
        binding.refreshControl.setOnRefreshListener(viewModel::loadRepos)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setTitle(null)
        setBackEnabled(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.adapter = null
    }

    private fun showError(message: String?) {
        message?.let {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
    }
}