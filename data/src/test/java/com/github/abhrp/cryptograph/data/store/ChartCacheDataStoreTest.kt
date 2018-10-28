package com.github.abhrp.cryptograph.data.store

import com.github.abhrp.cryptograph.data.factory.ChartFactory
import com.github.abhrp.cryptograph.data.factory.DataFactory
import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import com.github.abhrp.cryptograph.data.repository.ChartCache
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ChartCacheDataStoreTest {

    private val chartCache = mock<ChartCache>()
    private val chartCacheDataStore = ChartCacheDataStore(chartCache)

    @Test
    fun testGetChartCompletes() {
        val timeSpan = DataFactory.randomString()
        val chartData = ChartFactory.getRandomChartItemEntityList(10)
        stubGetChart(Single.just(chartData), timeSpan)
        val testObserver = chartCacheDataStore.getChart(timeSpan).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetChartReturnsData() {
        val timeSpan = DataFactory.randomString()
        val chartData = ChartFactory.getRandomChartItemEntityList(10)
        stubGetChart(Single.just(chartData), timeSpan)
        val testObserver = chartCacheDataStore.getChart(timeSpan).test()
        testObserver.assertValue(chartData)
    }

    @Test
    fun testGetChartCallsCache() {
        val timeSpan = DataFactory.randomString()
        val chartData = ChartFactory.getRandomChartItemEntityList(10)
        stubGetChart(Single.just(chartData), timeSpan)
        chartCacheDataStore.getChart(timeSpan).test()
        verify(chartCache).getChart(timeSpan)
    }

    @Test
    fun testGetChartPreferenceCompletes() {
        val chartPreferenceEntity = ChartFactory.getRandomChartPreferenceEntity()
        stubGetChartPreference(Single.just(chartPreferenceEntity))
        val testObserver = chartCacheDataStore.getChartPreference().test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetChartPreferenceReturnsData() {
        val chartPreferenceEntity = ChartFactory.getRandomChartPreferenceEntity()
        stubGetChartPreference(Single.just(chartPreferenceEntity))
        val testObserver = chartCacheDataStore.getChartPreference().test()
        testObserver.assertValue(chartPreferenceEntity)
    }

    @Test
    fun testGetChartPreferenceCallsCache() {
        val chartPreferenceEntity = ChartFactory.getRandomChartPreferenceEntity()
        stubGetChartPreference(Single.just(chartPreferenceEntity))
        chartCacheDataStore.getChartPreference().test()
        verify(chartCache).getChartPreference()
    }

    @Test
    fun testSetChartPreferenceCompletes() {
        val chartPreferenceEntity = ChartFactory.getRandomChartPreferenceEntity()
        stubSetChartPreference(Completable.complete(), chartPreferenceEntity)
        val testObserver = chartCacheDataStore.setChartPreference(chartPreferenceEntity).test()
        testObserver.assertComplete()
    }

    @Test
    fun testSetChartPreferenceCallsCache() {
        val chartPreferenceEntity = ChartFactory.getRandomChartPreferenceEntity()
        stubSetChartPreference(Completable.complete(), chartPreferenceEntity)
        chartCacheDataStore.setChartPreference(chartPreferenceEntity).test()
        verify(chartCache).setChartPreference(chartPreferenceEntity)
    }

    @Test
    fun testSaveChartCompletes() {
        val timeSpan = DataFactory.randomString()
        val chartData = ChartFactory.getRandomChartItemEntityList(100)
        stubSaveChart(Completable.complete(), timeSpan, chartData)
        val lastCacheTime = DataFactory.randomLong()
        stubSaveLastCacheTime(Completable.complete(), timeSpan, lastCacheTime)
        val testObserver = chartCacheDataStore.saveChart(timeSpan, chartData).test()
        testObserver.assertComplete()
    }

    @Test
    fun testSaveChartCallsCache() {
        val timeSpan = DataFactory.randomString()
        val chartData = ChartFactory.getRandomChartItemEntityList(100)
        stubSaveChart(Completable.complete(), timeSpan, chartData)
        val lastCacheTime = DataFactory.randomLong()
        stubSaveLastCacheTime(Completable.complete(), timeSpan, lastCacheTime)
        chartCacheDataStore.saveChart(timeSpan, chartData).test()
        verify(chartCache).saveChart(timeSpan, chartData)
    }

    @Test
    fun testClearChartCompletes() {
        val timeSpan = DataFactory.randomString()
        stubClearChart(Completable.complete(), timeSpan)
        val testObserver = chartCacheDataStore.clearChart(timeSpan).test()
        testObserver.assertComplete()
    }

    @Test
    fun testClearChartCallsCache() {
        val timeSpan = DataFactory.randomString()
        stubClearChart(Completable.complete(), timeSpan)
        chartCacheDataStore.clearChart(timeSpan).test()
        verify(chartCache).clearChart(timeSpan)
    }

    private fun stubGetChart(single: Single<List<ChartItemEntity>>, timeSpan: String) {
        whenever(chartCache.getChart(timeSpan)).thenReturn(single)
    }

    private fun stubSetChartPreference(completable: Completable, chartPreferenceEntity: ChartPreferenceEntity) {
        whenever(chartCache.setChartPreference(chartPreferenceEntity)).thenReturn(completable)
    }

    private fun stubGetChartPreference(single: Single<ChartPreferenceEntity>) {
        whenever(chartCache.getChartPreference()).thenReturn(single)
    }

    private fun stubSaveChart(completable: Completable, timeSpan: String, chartData: List<ChartItemEntity>) {
        whenever(chartCache.saveChart(timeSpan, chartData)).thenReturn(completable)
    }

    private fun stubSaveLastCacheTime(completable: Completable, timeSpan: String, lastCacheTime: Long) {
        whenever(chartCache.setLastCacheTime(timeSpan, lastCacheTime)).thenReturn(completable)
    }

    private fun stubClearChart(completable: Completable, timeSpan: String) {
        whenever(chartCache.clearChart(timeSpan)).thenReturn(completable)
    }
}