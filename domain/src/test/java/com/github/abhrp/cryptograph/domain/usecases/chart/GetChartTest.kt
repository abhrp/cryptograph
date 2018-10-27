package com.github.abhrp.cryptograph.domain.usecases.chart

import com.github.abhrp.cryptograph.domain.executor.PostExecutionThread
import com.github.abhrp.cryptograph.domain.factory.ChartFactory
import com.github.abhrp.cryptograph.domain.factory.DataFactory
import com.github.abhrp.cryptograph.domain.model.ChartItem
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
class GetChartTest {
    private lateinit var getChart: GetChart

    @Mock
    private lateinit var postExecutionThread: PostExecutionThread
    @Mock
    private lateinit var chartsRepository: ChartsRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getChart = GetChart(postExecutionThread, chartsRepository)
    }

    @Test
    fun testGetChartCompletes() {
        val timeSpan = DataFactory.randomString()
        val forceRefresh = DataFactory.randomBoolean()
        stubGetChart(Single.just(ChartFactory.getRandomChartItemList(10)), timeSpan, forceRefresh)
        val testObserver = getChart.buildUseCaseSingle(GetChart.Params.forChart(timeSpan, forceRefresh)).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetChartCallsRepositoryMethod() {
        val timeSpan = DataFactory.randomString()
        val forceRefresh = DataFactory.randomBoolean()
        stubGetChart(Single.just(ChartFactory.getRandomChartItemList(10)), timeSpan, forceRefresh)
        getChart.buildUseCaseSingle(GetChart.Params.forChart(timeSpan, forceRefresh)).test()
        verify(chartsRepository).getCharts(timeSpan, forceRefresh)
    }

    @Test
    fun testGetChartReturnsData() {
        val timeSpan = DataFactory.randomString()
        val forceRefresh = DataFactory.randomBoolean()
        val chartItemList = ChartFactory.getRandomChartItemList(10)
        stubGetChart(Single.just(chartItemList), timeSpan, forceRefresh)
        val testObserver = getChart.buildUseCaseSingle(GetChart.Params.forChart(timeSpan, forceRefresh)).test()
        testObserver.assertValue(chartItemList)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGetChartsThrowsIllegalArgumentException() {
        getChart.buildUseCaseSingle().test()
    }

    private fun stubGetChart(single: Single<List<ChartItem>>, timeSpan: String, forceRefresh: Boolean) {
        whenever(chartsRepository.getCharts(timeSpan, forceRefresh)).thenReturn(single)
    }
}