package test.trendingrepos

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import test.trendingrepos.common.di.DataModule
import test.trendingrepos.common.di.TestApiModule
import test.trendingrepos.common.di.UiModule
import javax.inject.Singleton

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

class UiTestApp : App() {

    override fun applicationInjector(): AndroidInjector<out UiTestApp> = DaggerTestAppComponent.builder().create(this)
}

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    TestApiModule::class,
    DataModule::class,
    UiModule::class
])
interface TestAppComponent : AndroidInjector<UiTestApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<UiTestApp>()

}