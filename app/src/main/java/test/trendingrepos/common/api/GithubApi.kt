package test.trendingrepos.common.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

interface GithubApi {

    @GET("/search/repositories")
    fun getRepositories(@Query("q") query: GithubDto.RepoQuery,
                        @Query("sort") sort: GithubDto.SortType,
                        @Query("order") order: GithubDto.SortOrder): Single<GithubDto.ListResponse<GithubDto.Repository>>

}