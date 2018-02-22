package test.trendingrepos.repos

import io.reactivex.Observable
import test.trendingrepos.OpenForTesting
import test.trendingrepos.common.api.GithubApi
import test.trendingrepos.common.api.GithubDto
import test.trendingrepos.utils.lets
import javax.inject.Inject

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

@OpenForTesting
class ReposModel @Inject constructor(private val api: GithubApi) {

    private var items: List<GithubDto.Repository>? = null

    var selectedIdx: Int? = null // TODO: persist selected index

    fun getSelectedRepo() : GithubDto.Repository? {
        return lets(selectedIdx, items) { idx, items -> items[idx] }
    }

    /**
     * May return another stream instead of simple API call
     * E.g. merge stream with cached data, etc
     */
    fun getRepos(): Observable<List<GithubDto.Repository>> {
        return api.getRepositories(GithubDto.RepoQuery("android"), GithubDto.SortType.STARS, GithubDto.SortOrder.DESC)
                .map { it.items }
                .doOnSuccess { items = it }
                .doOnError { items = null }
                .toObservable()
    }
}