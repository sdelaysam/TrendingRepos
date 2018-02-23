package test.trendingrepos.utils.repos

import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import test.trendingrepos.common.api.GithubApi
import test.trendingrepos.common.api.GithubDto
import test.trendingrepos.common.model.SessionModel
import test.trendingrepos.repos.ReposModel
import test.trendingrepos.stubs.getRepositoriesList
import test.trendingrepos.stubs.getRepositoriesListError
import test.trendingrepos.utils.ImmediateSchedulersRule

/**
 * Created on 22/02/2018
 * @author sdelaysam
 */

class ReposModelTest {

    @Rule
    @JvmField
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = ImmediateSchedulersRule()

    @Mock
    lateinit var api: GithubApi

    @Mock
    lateinit var session: SessionModel

    @Test
    fun getRepos() {
        doReturn(getRepositoriesList()).whenever(api).getRepositories(any(), any(), any())
        val model = ReposModel(api, session)
        val observer = TestObserver<List<GithubDto.Repository>>()
        model.getRepos().subscribe(observer)

        observer.assertComplete()
        observer.assertValueCount(1)
        observer.assertValue { it.size == 3 }
    }

    @Test
    fun keepSelectionAfterError() {
        doReturn(getRepositoriesList()).whenever(api).getRepositories(any(), any(), any())
        doReturn(1L).whenever(session).getLong(any())

        val model = ReposModel(api, session)
        val observer = TestObserver<List<GithubDto.Repository>>()
        model.getRepos().subscribe(observer)

        model.setSelectedId(1)
        verify(session).putLong(any(), eq(1))

        assertNotNull(model.getSelectedRepo())
        assertEquals("name1", model.getSelectedRepo().blockingFirst().toNullable()!!.name)

        doReturn(getRepositoriesListError("any")).whenever(api).getRepositories(any(), any(), any())
        val observer2 = TestObserver<List<GithubDto.Repository>>()
        model.getRepos(true).subscribe(observer2)
        observer2.assertError { it.message == "any" }

        verify(session, never()).remove(any())
        assertNotNull(model.getSelectedRepo())
        assertEquals("name1", model.getSelectedRepo().blockingFirst().toNullable()!!.name)
    }

}