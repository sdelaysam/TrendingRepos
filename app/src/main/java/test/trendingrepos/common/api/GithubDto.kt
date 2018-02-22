package test.trendingrepos.common.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

object GithubDto {

    data class ListResponse<out T> (
            @SerializedName("total_count") val totalCount: Int,
            @SerializedName("incomplete_results") val incompleteResults: Boolean,
            @SerializedName("items") val items: List<T>
    ) : Serializable

    data class Repository(
            @SerializedName("id") val id: Long,
            @SerializedName("name") val name: String,
            @SerializedName("full_name") val fullName: String,
            @SerializedName("owner") val owner: User,
            @SerializedName("private") val isPrivate: Boolean,
            @SerializedName("html_url") val htmlUrl: String,
            @SerializedName("description") val description: String,
            @SerializedName("fork") val isFork: Boolean,
            @SerializedName("url") val url: String,
            @SerializedName("created_at") val createdAt: Date,
            @SerializedName("updated_at") val updatedAt: Date,
            @SerializedName("pushed_at") val pushedAt: Date,
            @SerializedName("homepage") val homePage: String,
            @SerializedName("size") val size: Int,
            @SerializedName("stargazers_count") val stargazersCount: Int,
            @SerializedName("watchers_count") val watchersCount: Int,
            @SerializedName("language") val language: String,
            @SerializedName("forks_count") val forksCount: Int,
            @SerializedName("open_issues_count") val openIssuesCount: Int,
            @SerializedName("master_branch") val masterBranch: String,
            @SerializedName("default_branch") val defaultBranch: String,
            @SerializedName("score") val score: Double
    ) : Serializable

    data class User(
            @SerializedName("login") val login: String,
            @SerializedName("id") val id: Long,
            @SerializedName("avatar_url") val avatarUrl: String,
            @SerializedName("url") val url: String
    ) : Serializable

    data class RepoQuery(var keyword: String) {
        /**
         * Could be extended to support other search qualifiers
         * https://help.github.com/articles/search-syntax/
         */
        override fun toString() = keyword
    }

    enum class SortType {
        @SerializedName("stars") STARS,
        @SerializedName("forks") FORKS,
        @SerializedName("updated") UPDATED
    }

    enum class SortOrder {
        @SerializedName("asc") ASC,
        @SerializedName("desc") DESC
    }

}