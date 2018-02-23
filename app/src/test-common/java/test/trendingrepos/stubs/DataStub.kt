package test.trendingrepos.stubs

import io.reactivex.Single
import test.trendingrepos.common.api.GithubDto
import java.util.*

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

fun getEmptyRepositoriesList() = Single.defer {
    Single.just(GithubDto.ListResponse<GithubDto.Repository>(0, true, Collections.emptyList()))
}

fun getRepositoriesList() = Single.defer {
    val list = ArrayList<GithubDto.Repository>()
    list.add(GithubDto.Repository(1, "name1", "fullName1", getOwner(), false, "", "description1", false, "", Date(), Date(), Date(), "", 100, 1, 1, "", 1, 1, "", "", 10.0))
    list.add(GithubDto.Repository(2, "name2", "fullName2", getOwner(), false, "", "description2", false, "", Date(), Date(), Date(), "", 200, 2, 2, "", 2, 2, "", "", 20.0))
    list.add(GithubDto.Repository(3, "name3", "fullName3", getOwner(), false, "", "description3", false, "", Date(), Date(), Date(), "", 300, 3, 3, "", 3, 3, "", "", 30.0))
    Single.just(GithubDto.ListResponse(list.size, true, list))
}

fun getRepositoriesListError(message: String) = Single.defer {
    Single.error<GithubDto.ListResponse<GithubDto.Repository>>(Throwable(message))
}

fun getOwner() = GithubDto.User("owner", 1L, "", "")

fun getRepositoriesListObservable() = getRepositoriesList().map { it.items }.toObservable()