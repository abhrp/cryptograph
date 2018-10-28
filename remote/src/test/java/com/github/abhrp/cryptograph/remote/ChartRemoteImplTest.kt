package com.github.abhrp.cryptograph.remote

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.remote.factory.ChartResponseFactory
import com.github.abhrp.cryptograph.remote.factory.DataFactory
import com.github.abhrp.cryptograph.remote.mapper.ChartItemMapper
import com.github.abhrp.cryptograph.remote.model.ChartItem
import com.github.abhrp.cryptograph.remote.response.MarketPriceChartResponse
import com.github.abhrp.cryptograph.remote.service.ChartApiService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ChartRemoteImplTest {

    private val chartApiService = mock<ChartApiService>()
    private val chartItemMapper = mock<ChartItemMapper>()

    private val chartRemoteImpl = ChartRemoteImpl(chartApiService, chartItemMapper)

    @Test
    fun testGetChartCompletes() {
        val chartItem = ChartResponseFactory.getRandomChartItem()
        val chartItemEntity = ChartResponseFactory.getRandomChartItemEntity()
        val marketPriceChartResponse = ChartResponseFactory.getRandomMarketPriceChartResponse(listOf(chartItem))
        val timeSpan = DataFactory.randomString()
        stubGetMarketPriceChart(Single.just(marketPriceChartResponse), timeSpan)
        stunMapToEntity(chartItemEntity, chartItem)
        val testObserver = chartRemoteImpl.getChart(timeSpan).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetChartCallsApiServiceAndMapper() {
        val chartItem = ChartResponseFactory.getRandomChartItem()
        val chartItemEntity = ChartResponseFactory.getRandomChartItemEntity()
        val marketPriceChartResponse = ChartResponseFactory.getRandomMarketPriceChartResponse(listOf(chartItem))
        val timeSpan = DataFactory.randomString()
        stubGetMarketPriceChart(Single.just(marketPriceChartResponse), timeSpan)
        stunMapToEntity(chartItemEntity, chartItem)
        chartRemoteImpl.getChart(timeSpan).test()
        verify(chartApiService).getMarketPriceChart(timeSpan)
        verify(chartItemMapper).mapToEntity(chartItem)
    }

    @Test
    fun testGetChartReturnsData() {
        val chartItem = ChartResponseFactory.getRandomChartItem()
        val chartItemEntity = ChartResponseFactory.getRandomChartItemEntity()
        val marketPriceChartResponse = ChartResponseFactory.getRandomMarketPriceChartResponse(listOf(chartItem))
        val timeSpan = DataFactory.randomString()
        stubGetMarketPriceChart(Single.just(marketPriceChartResponse), timeSpan)
        stunMapToEntity(chartItemEntity, chartItem)
        val testObserver = chartRemoteImpl.getChart(timeSpan).test()
        testObserver.assertValue(listOf(chartItemEntity))
    }

    private fun stubGetMarketPriceChart(single: Single<MarketPriceChartResponse>, timeSpan: String) {
        whenever(chartApiService.getMarketPriceChart(timeSpan)).thenReturn(single)
    }

    private fun stunMapToEntity(chartItemEntity: ChartItemEntity, chartItem: ChartItem) {
        whenever(chartItemMapper.mapToEntity(chartItem)).thenReturn(chartItemEntity)
    }
}