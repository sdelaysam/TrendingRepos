package test.trendingrepos.common.di

import dagger.Module
import dagger.Provides
import test.trendingrepos.common.api.GithubApi
import test.trendingrepos.common.model.SessionModel
import test.trendingrepos.repos.ReposModel
import javax.inject.Singleton

/**
 * Dagger dependency module providing application's models.
 * @see <a href="https://google.github.io/dagger/">Dagger documentation</a>
 *
 * Created on 22/02/2018
 * @author sdelaysam
 */

@Module
class DataModule {

    @Provides
    @Singleton
    fun providesReposModel(api: GithubApi, sessionModel: SessionModel) = ReposModel(api, sessionModel)

    @Provides
    @Singleton
    fun providesSessionModel() = SessionModel()

}