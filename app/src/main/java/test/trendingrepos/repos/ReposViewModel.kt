package test.trendingrepos.repos

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import test.trendingrepos.OpenForTesting
import test.trendingrepos.R
import test.trendingrepos.common.api.GithubDto
import test.trendingrepos.main.Segue
import test.trendingrepos.main.SegueType
import javax.inject.Inject

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

@OpenForTesting
class ReposViewModel @Inject constructor(private val model: ReposModel) : ViewModel() {

    val empty = ObservableBoolean(false)
    val loading = ObservableBoolean(false)
    var emptyText = ObservableField(R.string.no_repos)
    val error = MutableLiveData<String>()
    val segue = MutableLiveData<Segue>()
    val adapter = ReposAdapter({ idx -> doOnSelection(idx) })

    private var requestDisposable = Disposables.disposed()

    init { loadRepos() }

    fun loadRepos() {
        if (requestDisposable.isDisposed) {
            requestDisposable = model.getRepos()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { loading.set(true) }
                    .doAfterTerminate { loading.set(false) }
                    .subscribe(this::doOnSuccess, this::doOnError)
        }
    }

    private fun doOnSuccess(response: List<GithubDto.Repository>) {
        empty.set(response.isEmpty())
        emptyText.set(R.string.no_repos)
        adapter.repos = response
    }

    private fun doOnError(throwable: Throwable) {
        if (adapter.itemCount == 0) {
            empty.set(true)
            emptyText.set(R.string.repos_failed)
        }
        error.postValue(throwable.message)
    }

    private fun doOnSelection(index: Int) {
        model.selectedIdx = index
        segue.value = Segue(SegueType.OPEN_DETAILS)
    }

    override fun onCleared() {
        super.onCleared()
        requestDisposable.dispose()
    }
}