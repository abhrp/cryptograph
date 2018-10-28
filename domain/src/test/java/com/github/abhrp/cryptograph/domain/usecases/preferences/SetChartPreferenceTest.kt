package com.github.abhrp.cryptograph.domain.usecases.preferences

import com.github.abhrp.cryptograph.domain.executor.PostExecutionThread
import com.github.abhrp.cryptograph.domain.factory.DataFactory
import com.github.abhrp.cryptograph.domain.model.ChartPreference
import com.github.abhrp.cryptograph.domain.repository.ChartsRepository
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class SetChartPreferenceTest {

    private lateinit var setChartPreference: SetChartPreference

    @Mock
    private lateinit var postExecutionThread: PostExecutionThread
    @Mock
    private lateinit var chartsRepository: ChartsRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        setChartPreference = SetChartPreference(postExecutionThread, chartsRepository)
    }

    @Test
    fun testSetChartPreferenceCompletes() {
        val timeSpan = DataFactory.randomString()
        val chartPreference = ChartPreference(timeSpan)
        stubSetChartPreference(Completable.complete(), chartPreference)
        val testObserver =
            setChartPreference.buildUseCaseCompletable(SetChartPreference.Params.forTimeSpan(timeSpan)).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun testSetChartPreferenceThrowsIllegalArgumentException() {
        setChartPreference.buildUseCaseCompletable(null).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun testSetChartPreferenceThrowsIllegalArgumentExceptionIfTimeSpanIsEmpty() {
        setChartPreference.buildUseCaseCompletable(SetChartPreference.Params("")).test()
    }

    @Test(expected = IllegalArgumentException::class)
    fun testSetChartPreferenceThrowsIllegalArgumentExceptionIfTimeSpanHasWhiteSpace() {
        setChartPreference.buildUseCaseCompletable(SetChartPreference.Params("   ")).test()
    }

    private fun stubSetChartPreference(completable: Completable, chartPreference: ChartPreference) {
        whenever(chartsRepository.setChartsPreference(chartPreference)).thenReturn(completable)
    }
}