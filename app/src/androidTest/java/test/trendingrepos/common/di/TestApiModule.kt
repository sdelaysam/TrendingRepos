package test.trendingrepos.common.di

import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import test.trendingrepos.common.api.GithubApi
import javax.inject.Singleton

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

@Module
class TestApiModule {

    companion object {
        val githubApi = mock<GithubApi> {  }
    }

    @Provides
    @Singleton
    fun providesGithubApi() = githubApi

}
