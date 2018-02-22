package test.trendingrepos.repos

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import test.trendingrepos.OpenForTesting
import javax.inject.Inject

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

@OpenForTesting
class ReposViewModel @Inject constructor(private val model: ReposModel) : ViewModel() {

    val empty = ObservableBoolean(false)

    private val disposables = CompositeDisposable()

    init {
        disposables.add(model.getRepos()
                .subscribeOn(Schedulers.io())
                .subscribe {
                    empty.set(it.isEmpty())
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}