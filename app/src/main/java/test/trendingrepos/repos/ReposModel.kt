package test.trendingrepos.repos

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import test.trendingrepos.OpenForTesting
import test.trendingrepos.common.api.GithubApi
import test.trendingrepos.common.api.GithubDto
import test.trendingrepos.common.model.SessionModel
import javax.inject.Inject

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

@OpenForTesting
class ReposModel @Inject constructor(private val api: GithubApi,
                                     private val sessionModel: SessionModel) {

    private val selectedIdKey = "selected_id"

    private var items: List<GithubDto.Repository>? = null

    fun setSelectedId(id: Long?) {
        if (id != null) {
            sessionModel.putLong(selectedIdKey, id)
        } else {
            sessionModel.remove(selectedIdKey)
        }
    }

    fun getSelectedId() = sessionModel.getLong(selectedIdKey)

    /**
     * May return another stream instead of simple API call
     * E.g. merge stream with cached data, etc
     */
    fun getRepos(forceUpdate: Boolean = false): Observable<List<GithubDto.Repository>> {
        if (forceUpdate || items == null) {
            return api.getRepositories(GithubDto.RepoQuery("android"), GithubDto.SortType.STARS, GithubDto.SortOrder.DESC)
                    .subscribeOn(Schedulers.io())
                    .map { it.items }
                    .doOnSuccess { items = it }
                    .toObservable()
        }
        return Single.just(items!!).toObservable()
    }

    fun getSelectedRepo(): Observable<Optional<GithubDto.Repository>> {
        val id = getSelectedId()
        if (id != null) {
            return getRepos().map {
                it.find { repository -> repository.id == id }.toOptional()
            }
        }
        return Observable.just(None)
    }
}