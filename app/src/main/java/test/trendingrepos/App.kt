package test.trendingrepos

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import test.trendingrepos.common.di.ApiModule
import test.trendingrepos.common.di.DataModule
import test.trendingrepos.common.di.UiModule
import javax.inject.Singleton

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

@OpenForTesting
class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out App> = DaggerAppComponent.builder().create(this)
}

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApiModule::class,
    DataModule::class,
    UiModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

}