package test.trendingrepos.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import test.trendingrepos.R
import test.trendingrepos.common.ext.performSegue
import test.trendingrepos.common.ext.setTitle
import test.trendingrepos.common.ext.setBackEnabled
import test.trendingrepos.databinding.FragmentDetailsBinding
import javax.inject.Inject

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

class DetailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DetailsViewModel

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
        viewModel.title.observe(this, Observer { title -> setTitle(title) })
        viewModel.segue.observe(this, Observer { segue -> performSegue(segue) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setBackEnabled(true)
    }
}