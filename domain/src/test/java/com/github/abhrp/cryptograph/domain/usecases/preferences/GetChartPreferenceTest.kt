package com.github.abhrp.cryptograph.domain.usecases.preferences

import com.github.abhrp.cryptograph.domain.executor.PostExecutionThread
import com.github.abhrp.cryptograph.domain.factory.ChartFactory
import com.github.abhrp.cryptograph.domain.model.ChartPreference
import com.github.abhrp.cryptograph.domain.repository.ChartsRepository
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetChartPreferenceTest {

    private lateinit var getChartPreference: GetChartPreference

    @Mock
    private lateinit var postExecutionThread: PostExecutionThread
    @Mock
    private lateinit var chartsRepository: ChartsRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getChartPreference = GetChartPreference(postExecutionThread, chartsRepository)
    }

    @Test
    fun testGetChartPreferenceCompletes() {
        val chartPreference = ChartFactory.getRandomChartPreference()
        stubGetChartPreference(Single.just(chartPreference))
        val testObserver = getChartPreference.buildUseCaseSingle().test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetChartPreferenceReturnsData() {
        val chartPreference = ChartFactory.getRandomChartPreference()
        stubGetChartPreference(Single.just(chartPreference))
        val testObserver = getChartPreference.buildUseCaseSingle().test()
        testObserver.assertValue(chartPreference)
    }

    @Test
    fun testGetChartPreferenceCallsRepositoryMethod() {
        val chartPreference = ChartFactory.getRandomChartPreference()
        stubGetChartPreference(Single.just(chartPreference))
        getChartPreference.buildUseCaseSingle().test()
        verify(chartsRepository).getChartsPreference()
    }

    private fun stubGetChartPreference(single: Single<ChartPreference>) {
        whenever(chartsRepository.getChartsPreference()).thenReturn(single)
    }
}