package test.trendingrepos.details

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import com.gojuno.koptional.Optional
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import test.trendingrepos.common.api.GithubDto
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

    private var disposable = Disposables.empty()

    init {
        disposable = model.getSelectedRepo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::doOnRepo)
    }

    private fun doOnRepo(repo: Optional<GithubDto.Repository>) {
        val r = repo.toNullable()
        if (r != null) {
            title.value = r.name
            fullName.set(r.fullName)
            description.set(r.description)
        } else {
            // selected index is not persisted so process restart will invalidate data in model
            // should be fixed in real-life app :)
            segue.value = Segue(SegueType.GO_BACK)
        }
    }

    override fun onCleared() {
        super.onCleared()
        model.setSelectedId(null)
        disposable.dispose()
    }

}