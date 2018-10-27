package com.github.abhrp.cryptograph.data

import com.github.abhrp.cryptograph.data.factory.ChartFactory
import com.github.abhrp.cryptograph.data.factory.DataFactory
import com.github.abhrp.cryptograph.data.mapper.ChartItemEntityMapper
import com.github.abhrp.cryptograph.data.mapper.ChartPreferenceEntityMapper
import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import com.github.abhrp.cryptograph.data.repository.ChartCache
import com.github.abhrp.cryptograph.data.repository.ChartDataStore
import com.github.abhrp.cryptograph.data.store.ChartCacheDataStore
import com.github.abhrp.cryptograph.data.store.ChartDataStoreFactory
import com.github.abhrp.cryptograph.domain.model.ChartItem
import com.github.abhrp.cryptograph.domain.model.ChartPreference
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ChartDataRepositoryTest {
    private val chartDataStore = mock<ChartDataStore>()
    private val chartCacheDataStore = mock<ChartCacheDataStore>()
    private val chartCache = mock<ChartCache>()
    private val chartDataStoreFactory = mock<ChartDataStoreFactory>()
    private val chartItemEntityMapper = mock<ChartItemEntityMapper>()
    private val chartPreferenceEntityMapper = mock<ChartPreferenceEntityMapper>()

    private val chartDataRepository =
        ChartDataRepository(chartCache, chartDataStoreFactory, chartItemEntityMapper, chartPreferenceEntityMapper)

    @Test
    fun testGetChartsCompletes() {
        val timeSpan = DataFactory.randomString()
        val isCached = DataFactory.randomBoolean()
        val isExpired = DataFactory.randomBoolean()
        val forceRefresh = DataFactory.randomBoolean()
        val chartItemEntity = ChartFactory.getRandomChartItemEntity()
        val chartItem = ChartFactory.getRandomChartItem()
        stubChartCached(Single.just(isCached), timeSpan)
        stubCacheExpired(Single.just(isExpired), timeSpan)
        stubGetChartDataStore(chartDataStore, isCached, isExpired, forceRefresh)
        stubGetChartDataFromDataSore(Single.just(listOf(chartItemEntity)), timeSpan)
        stubSaveChart(listOf(chartItemEntity), timeSpan, Completable.complete())
        stubMapToDomain(chartItem, chartItemEntity)

        val testObserver = chartDataRepository.getCharts(timeSpan, forceRefresh).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetChartsReturnsData() {
        val timeSpan = DataFactory.randomString()
        val isCached = DataFactory.randomBoolean()
        val isExpired = DataFactory.randomBoolean()
        val forceRefresh = DataFactory.randomBoolean()
        val chartItemEntity = ChartFactory.getRandomChartItemEntity()
        val chartItem = ChartFactory.getRandomChartItem()
        stubChartCached(Single.just(isCached), timeSpan)
        stubCacheExpired(Single.just(isExpired), timeSpan)
        stubGetChartDataStore(chartDataStore, isCached, isExpired, forceRefresh)
        stubGetChartDataFromDataSore(Single.just(listOf(chartItemEntity)), timeSpan)
        stubSaveChart(listOf(chartItemEntity), timeSpan, Completable.complete())
        stubMapToDomain(chartItem, chartItemEntity)
        val testObserver = chartDataRepository.getCharts(timeSpan, forceRefresh).test()
        testObserver.assertValue(listOf(chartItem))
    }

    @Test
    fun testGetChartPreferenceCompletes() {
        stubGetCacheDataStore(chartCacheDataStore)
        val chartPreferenceEntity = ChartFactory.getRandomChartPreferenceEntity()
        stubGetChartPreference(Single.just(chartPreferenceEntity))
        val chartPreference = ChartFactory.getRandomChartPreference()
        stubMapToChartPreference(chartPreference, chartPreferenceEntity)

        val testObserver = chartDataRepository.getChartsPreference().test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetChartPreferenceReturnsData() {
        stubGetCacheDataStore(chartCacheDataStore)
        val chartPreferenceEntity = ChartFactory.getRandomChartPreferenceEntity()
        stubGetChartPreference(Single.just(chartPreferenceEntity))
        val chartPreference = ChartFactory.getRandomChartPreference()
        stubMapToChartPreference(chartPreference, chartPreferenceEntity)

        val testObserver = chartDataRepository.getChartsPreference().test()
        testObserver.assertValue(chartPreference)
    }


    private fun stubChartCached(single: Single<Boolean>, timeSpan: String) {
        whenever(chartCache.isChartCached(timeSpan)).thenReturn(single)
    }

    private fun stubCacheExpired(single: Single<Boolean>, timeSpan: String) {
        whenever(chartCache.isCacheExpired(timeSpan)).thenReturn(single)
    }

    private fun stubGetChartDataStore(
        chartDataStore: ChartDataStore,
        cached: Boolean,
        expired: Boolean,
        forceRefresh: Boolean
    ) {
        whenever(chartDataStoreFactory.getDataStore(cached, expired, forceRefresh)).thenReturn(chartDataStore)
    }

    private fun stubGetChartDataFromDataSore(single: Single<List<ChartItemEntity>>, timeSpan: String) {
        whenever(chartDataStore.getChart(timeSpan)).thenReturn(single)
    }

    private fun stubSaveChart(chartData: List<ChartItemEntity>, timeSpan: String, completable: Completable) {
        whenever(chartCacheDataStore.saveChart(timeSpan, chartData)).thenReturn(completable)
    }

    private fun stubMapToDomain(domain: ChartItem, entity: ChartItemEntity) {
        whenever(chartItemEntityMapper.mapToDomain(entity)).thenReturn(domain)
    }

    private fun stubGetCacheDataStore(cacheDataStore: ChartCacheDataStore) {
        whenever(chartDataStoreFactory.getCacheDataStore()).thenReturn(cacheDataStore)
    }

    private fun stubGetChartPreference(single: Single<ChartPreferenceEntity>) {
        whenever(chartCacheDataStore.getChartPreference()).thenReturn(single)
    }

    private fun stubMapToChartPreference(domain: ChartPreference, entity: ChartPreferenceEntity) {
        whenever(chartPreferenceEntityMapper.mapToDomain(entity)).thenReturn(domain)
    }
}