package test.trendingrepos.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import test.trendingrepos.main.Segue
import test.trendingrepos.main.SegueType
import test.trendingrepos.repos.ReposModel
import javax.inject.Inject

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

class DetailsViewModel @Inject constructor(private val model: ReposModel) : ViewModel() {

    val title = MutableLiveData<String>()

    val segue = MutableLiveData<Segue>()

    val fullName = ObservableField("")

    val description = ObservableField("")

    init {
        val repo = model.getSelectedRepo()
        if (repo != null) {
            title.value = repo.name
            fullName.set(repo.fullName)
            description.set(repo.description)
        } else {
            // selected index is not persisted so process restart will invalidate data in model
            // should be fixed in real-life app :)
            segue.value = Segue(SegueType.GO_BACK)
        }
    }

    override fun onCleared() {
        super.onCleared()
        model.selectedIdx = null
    }

}