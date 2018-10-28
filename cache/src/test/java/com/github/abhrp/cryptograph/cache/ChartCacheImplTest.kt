package com.github.abhrp.cryptograph.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.github.abhrp.cryptograph.cache.factory.ChartCacheFactory
import com.github.abhrp.cryptograph.cache.factory.DataFactory
import com.github.abhrp.cryptograph.cache.mapper.ChartItemMapper
import com.github.abhrp.cryptograph.cache.model.CachedChartItem
import com.github.abhrp.cryptograph.cache.sharedpreferences.ChartSharedPreferences
import com.github.abhrp.cryptograph.cache.sql.ChartDatabase
import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class ChartCacheImplTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ChartDatabase
    private val chartSharedPreferences = mock<ChartSharedPreferences>()
    private val chartItemMapper = mock<ChartItemMapper>()

    private lateinit var chartCacheImpl: ChartCacheImpl

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ChartDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        chartCacheImpl = ChartCacheImpl(database, chartSharedPreferences, chartItemMapper)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetChartCompletes() {
        val chartItemEntity = ChartCacheFactory.getRandomChartItemEntity()
        val timeSpan = DataFactory.randomString()
        val cachedChartItem = CachedChartItem(1, timeSpan, chartItemEntity.datetime, chartItemEntity.value)
        database.getChartItemDao().insertChartItem(listOf(cachedChartItem))
        stubMapToEntity(cachedChartItem, chartItemEntity)
        val testObserver = chartCacheImpl.getChart(timeSpan).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetChartReturnsData() {
        val chartItemEntity = ChartCacheFactory.getRandomChartItemEntity()
        val timeSpan = DataFactory.randomString()
        val cachedChartItem = CachedChartItem(1, timeSpan, chartItemEntity.datetime, chartItemEntity.value)
        database.getChartItemDao().insertChartItem(listOf(cachedChartItem))
        stubMapToEntity(cachedChartItem, chartItemEntity)
        val testObserver = chartCacheImpl.getChart(timeSpan).test()
        testObserver.assertValue(listOf(chartItemEntity))
    }

    @Test
    fun testGetChartPreferenceCompletes() {
        val timeSpan = DataFactory.randomString()
        stubLastTimeSpanSelection(timeSpan)
        val testObserver = chartCacheImpl.getChartPreference().test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetChartPreferenceReturnsData() {
        val timeSpan = DataFactory.randomString()
        val chartPreferenceEntity = ChartPreferenceEntity(timeSpan)
        stubLastTimeSpanSelection(timeSpan)
        val testObserver = chartCacheImpl.getChartPreference().test()
        testObserver.assertValue(chartPreferenceEntity)
    }

    @Test
    fun testClearChartCompletes() {
        val chartItemEntity = ChartCacheFactory.getRandomChartItemEntity()
        val timeSpan = DataFactory.randomString()
        val cachedChartItem = CachedChartItem(1, timeSpan, chartItemEntity.datetime, chartItemEntity.value)
        database.getChartItemDao().insertChartItem(listOf(cachedChartItem))
        database.getChartItemDao().deleteChartItems(timeSpan)
        val testObserver2 = chartCacheImpl.clearChart(timeSpan).test()
        testObserver2.assertComplete()
    }

    @Test
    fun testClearChartRemovesData() {
        val chartItemEntity = ChartCacheFactory.getRandomChartItemEntity()
        val timeSpan = DataFactory.randomString()
        val cachedChartItem = CachedChartItem(1, timeSpan, chartItemEntity.datetime, chartItemEntity.value)
        database.getChartItemDao().insertChartItem(listOf(cachedChartItem))
        database.getChartItemDao().deleteChartItems(timeSpan)
        val testObserver1 = chartCacheImpl.clearChart(timeSpan).test()
        testObserver1.assertComplete()
        val testObserver2 = chartCacheImpl.getChart(timeSpan).test()
        testObserver2.assertValue(emptyList())
    }

    @Test
    fun testSaveChartInsertsChartData() {
        val chartItemEntity = ChartCacheFactory.getRandomChartItemEntity()
        val timeSpan = DataFactory.randomString()
        val cachedChartItem = CachedChartItem(1, timeSpan, chartItemEntity.datetime, chartItemEntity.value)
        stubMapToCache(cachedChartItem, chartItemEntity, timeSpan)
        val testObserver = chartCacheImpl.saveChart(timeSpan, listOf(chartItemEntity)).test()
        testObserver.assertComplete()
    }

    @Test
    fun testSaveChartReturnsData() {
        val chartItemEntity = ChartCacheFactory.getRandomChartItemEntity()
        val timeSpan = DataFactory.randomString()
        val cachedChartItem = CachedChartItem(1, timeSpan, chartItemEntity.datetime, chartItemEntity.value)
        stubMapToCache(cachedChartItem, chartItemEntity, timeSpan)
        chartCacheImpl.saveChart(timeSpan, listOf(chartItemEntity)).test()
        stubMapToEntity(cachedChartItem, chartItemEntity)
        val testObserver = chartCacheImpl.getChart(timeSpan).test()
        testObserver.assertValue(listOf(chartItemEntity))
    }

    @Test
    fun testIsChartCachedCompletes() {
        val timeSpan = DataFactory.randomString()
        val testObserver = chartCacheImpl.isChartCached(timeSpan).test()
        testObserver.assertComplete()
    }

    @Test
    fun testIsChartCachedReturnsTrue() {
        val chartItemEntity = ChartCacheFactory.getRandomChartItemEntity()
        val timeSpan = DataFactory.randomString()
        val cachedChartItem = CachedChartItem(1, timeSpan, chartItemEntity.datetime, chartItemEntity.value)
        stubMapToCache(cachedChartItem, chartItemEntity, timeSpan)
        chartCacheImpl.saveChart(timeSpan, listOf(chartItemEntity)).test()
        val testObserver = chartCacheImpl.isChartCached(timeSpan).test()
        testObserver.assertValue(true)
    }

    @Test
    fun testSetLastCacheTimeCompletes() {
        val timeSpan = DataFactory.randomString()
        val lastCacheTime = DataFactory.randomLong()
        val testObserver = chartCacheImpl.setLastCacheTime(timeSpan, lastCacheTime).test()
        testObserver.assertComplete()
    }

    @Test
    fun testSetLastCacheTimeSavesTimeStampCorrectly() {
        val timeSpan = DataFactory.randomString()
        val lastCacheTime = DataFactory.randomLong()
        chartCacheImpl.setLastCacheTime(timeSpan, lastCacheTime).test()
        val cacheTime = database.getCacheTimeDao().getCacheTime(timeSpan).blockingGet()
        assertEquals(cacheTime.timestamp, lastCacheTime)
        assertEquals(cacheTime.timeSpan, timeSpan)
    }

    @Test
    fun testIsCacheExpiredCompletes() {
        val timeSpan = DataFactory.randomString()
        val lastCacheTime = DataFactory.randomLong()
        chartCacheImpl.setLastCacheTime(timeSpan, lastCacheTime).test()
        val testObserver = chartCacheImpl.isCacheExpired(timeSpan).test()
        testObserver.assertComplete()
    }

    @Test
    fun testIsCacheExpiredReturnsTrue() {
        val timeSpan = DataFactory.randomString()
        val lastCacheTime = System.currentTimeMillis() - (10 * 60 * 1000)
        chartCacheImpl.setLastCacheTime(timeSpan, lastCacheTime).test()
        val testObserver = chartCacheImpl.isCacheExpired(timeSpan).test()
        testObserver.assertValue(true)
    }

    @Test
    fun testIsCacheExpiredReturnsFalse() {
        val timeSpan = DataFactory.randomString()
        val lastCacheTime = System.currentTimeMillis() - (1 * 60 * 1000)
        chartCacheImpl.setLastCacheTime(timeSpan, lastCacheTime).test()
        val testObserver = chartCacheImpl.isCacheExpired(timeSpan).test()
        testObserver.assertValue(false)
    }

    private fun stubMapToEntity(cachedChartItem: CachedChartItem, chartItemEntity: ChartItemEntity) {
        whenever(chartItemMapper.mapToEntity(cachedChartItem)).thenReturn(chartItemEntity)
    }

    private fun stubLastTimeSpanSelection(lastTimeSpanSelection: String) {
        whenever(chartSharedPreferences.lastTimeSpanSelection).thenReturn(lastTimeSpanSelection)
    }

    private fun stubMapToCache(cachedChartItem: CachedChartItem, chartItemEntity: ChartItemEntity, timeSpan: String) {
        whenever(chartItemMapper.mapToCache(timeSpan, chartItemEntity)).thenReturn(cachedChartItem)
    }
}