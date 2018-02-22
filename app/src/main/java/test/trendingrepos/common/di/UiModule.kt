package test.trendingrepos.common.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import test.trendingrepos.main.MainActivity
import test.trendingrepos.common.viewmodel.ViewModelFactory
import test.trendingrepos.common.viewmodel.ViewModelKey
import test.trendingrepos.details.DetailsFragment
import test.trendingrepos.details.DetailsViewModel
import test.trendingrepos.main.MainViewModel
import test.trendingrepos.repos.ReposFragment
import test.trendingrepos.repos.ReposViewModel

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

@Module
interface UiModule {

    @ContributesAndroidInjector
    fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun contributesReposFragment(): ReposFragment

    @ContributesAndroidInjector
    fun contributesDetailsFragment(): DetailsFragment

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindsMainViewModel(viewModel: MainViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReposViewModel::class)
    fun bindsReposViewModel(viewModel: ReposViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindsDetailsViewModel(viewModel: DetailsViewModel) : ViewModel

    @Binds
    fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}