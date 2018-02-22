package test.trendingrepos.common.viewmodel

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * https://medium.com/@alexfacciorusso/understanding-dagger-2-multibindings-viewmodel-8418eb372848
 * Created on 22/02/2018
 * @author sdelaysam
 */

@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)