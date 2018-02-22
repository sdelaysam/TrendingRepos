package test.trendingrepos.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

class MainViewModel @Inject constructor() : ViewModel() {

    val title = MutableLiveData<String?>()

    var backEnabled = MutableLiveData<Boolean?>()

    val segue = MutableLiveData<Segue?>()

}