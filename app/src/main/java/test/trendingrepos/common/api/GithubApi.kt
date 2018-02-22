package test.trendingrepos.common.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Github API v3
 * Refer to <a href="https://developer.github.com/v3/">Github documentation</a>
 *
 * Created on 22/02/2018
 * @author sdelaysam
 */

interface GithubApi {

    @GET("/search/repositories")
    fun getRepositories(@Query("q") query: GithubDto.RepoQuery,
                        @Query("sort") sort: GithubDto.SortType,
                        @Query("order") order: GithubDto.SortOrder): Single<GithubDto.ListResponse<GithubDto.Repository>>

}