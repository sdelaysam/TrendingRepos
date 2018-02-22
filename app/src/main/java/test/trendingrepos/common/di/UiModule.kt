package test.trendingrepos.common.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import test.trendingrepos.common.viewmodel.ViewModelFactory
import test.trendingrepos.common.viewmodel.ViewModelKey
import test.trendingrepos.repos.ReposFragment
import test.trendingrepos.repos.ReposViewModel

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

@Module
interface UiModule {

    @ContributesAndroidInjector
    fun contributeReposFragment(): ReposFragment

    @Binds
    @IntoMap
    @ViewModelKey(ReposViewModel::class)
    fun bindListUsersViewModel(viewModel: ReposViewModel) : ViewModel

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}