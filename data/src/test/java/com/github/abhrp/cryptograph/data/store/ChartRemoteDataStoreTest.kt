package com.github.abhrp.cryptograph.data.store

import com.github.abhrp.cryptograph.data.factory.ChartFactory
import com.github.abhrp.cryptograph.data.factory.DataFactory
import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import com.github.abhrp.cryptograph.data.repository.ChartRemote
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ChartRemoteDataStoreTest {
    private val chartRemote = mock<ChartRemote>()
    private val chartRemoteDataStore = ChartRemoteDataStore(chartRemote)

    @Test
    fun testGetChartCompletes() {
        val timeSpan = DataFactory.randomString()
        val chartData = ChartFactory.getRandomChartItemEntityList(10)
        stubGetChart(Single.just(chartData), timeSpan)
        val testObserver = chartRemoteDataStore.getChart(timeSpan).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetChartReturnsData() {
        val timeSpan = DataFactory.randomString()
        val chartData = ChartFactory.getRandomChartItemEntityList(10)
        stubGetChart(Single.just(chartData), timeSpan)
        val testObserver = chartRemoteDataStore.getChart(timeSpan).test()
        testObserver.assertValue(chartData)
    }

    @Test
    fun testGetChartCallsRemote() {
        val timeSpan = DataFactory.randomString()
        val chartData = ChartFactory.getRandomChartItemEntityList(10)
        stubGetChart(Single.just(chartData), timeSpan)
        chartRemoteDataStore.getChart(timeSpan).test()
        verify(chartRemote).getChart(timeSpan)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testGetChartPreferenceThrowsUnsupportedOperationException() {
        chartRemoteDataStore.getChartPreference().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testClearChartThrowsUnsupportedOperationException() {
        chartRemoteDataStore.clearChart(DataFactory.randomString()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testSaveChartThrowsUnsupportedOperationException() {
        chartRemoteDataStore.saveChart(DataFactory.randomString(), ChartFactory.getRandomChartItemEntityList(100))
            .test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testSetChartPreferenceThrowsUnsupportedOperationException() {
        chartRemoteDataStore.setChartPreference(ChartPreferenceEntity("")).test()
    }

    private fun stubGetChart(single: Single<List<ChartItemEntity>>, timeSpan: String) {
        whenever(chartRemote.getChart(timeSpan)).thenReturn(single)
    }
}