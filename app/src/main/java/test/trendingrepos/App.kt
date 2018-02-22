package test.trendingrepos

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
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
    AndroidSupportInjectionModule::class
])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

}