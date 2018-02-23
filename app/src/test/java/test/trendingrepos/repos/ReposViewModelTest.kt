package test.trendingrepos.repos

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import test.trendingrepos.stubs.getRepositoriesListError
import test.trendingrepos.stubs.getRepositoriesListObservable
import test.trendingrepos.utils.ImmediateSchedulersRule

/**
 * Created on 23/02/2018
 * @author sdelaysam
 */

class ReposViewModelTest {

    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = ImmediateSchedulersRule()

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    @Mock
    lateinit var model: ReposModel

    @Test
    fun loadSuccess() {
        doReturn(getRepositoriesListObservable()).whenever(model).getRepos()
        val viewModel = ReposViewModel(model)

        assertFalse(viewModel.empty.get())
        assertFalse(viewModel.loading.get())
        assertNull(viewModel.segue.value)
        assertNull(viewModel.error.value)
        assertEquals(3, viewModel.adapter.itemCount)
    }

    @Test
    fun loadError() {
        val message = "Network error"
        doReturn(getRepositoriesListError(message).toObservable()).whenever(model).getRepos()
        val viewModel = ReposViewModel(model)

        assertTrue(viewModel.empty.get())
        assertFalse(viewModel.loading.get())
        assertNull(viewModel.segue.value)
        assertNotNull(viewModel.error.value)
        assertEquals(message, viewModel.error.value!!)
        assertEquals(0, viewModel.adapter.itemCount)
    }

}