package test.trendingrepos.common.di

import dagger.Module
import dagger.Provides
import test.trendingrepos.common.api.GithubApi
import test.trendingrepos.repos.ReposModel
import javax.inject.Singleton

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesReposModel(api: GithubApi) = ReposModel(api)

}