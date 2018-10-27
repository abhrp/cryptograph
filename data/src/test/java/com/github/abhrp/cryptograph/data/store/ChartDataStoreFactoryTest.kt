package com.github.abhrp.cryptograph.data.store

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ChartDataStoreFactoryTest {

    private val chartCacheDataStore = mock<ChartCacheDataStore>()
    private val chartRemoteDataStore = mock<ChartRemoteDataStore>()
    private val chartDataStoreFactory = ChartDataStoreFactory(chartCacheDataStore, chartRemoteDataStore)

    @Test
    fun testChartDataStoreFactoryReturnsRemoteDataStoreIfForceRefreshIsTrue() {
        assertEquals(chartDataStoreFactory.getDataStore(true, true, true), chartRemoteDataStore)
        assertEquals(chartDataStoreFactory.getDataStore(true, false, true), chartRemoteDataStore)
        assertEquals(chartDataStoreFactory.getDataStore(false, true, true), chartRemoteDataStore)
        assertEquals(chartDataStoreFactory.getDataStore(false, false, true), chartRemoteDataStore)
    }

    @Test
    fun testChartDataStoreFactoryReturnsRemoteIfCacheIsExpired() {
        assertEquals(chartDataStoreFactory.getDataStore(true, true, false), chartRemoteDataStore)
    }

    @Test
    fun testChartDataStoreFactoryReturnsRemoteIfChartNotCached() {
        assertEquals(chartDataStoreFactory.getDataStore(false, true, false), chartRemoteDataStore)
        assertEquals(chartDataStoreFactory.getDataStore(false, false, false), chartRemoteDataStore)
    }

    @Test
    fun testChartDataStoreFactoryReturnsCacheIfCacheExistsAndCacheNotExpired() {
        assertEquals(chartDataStoreFactory.getDataStore(true, false, false), chartCacheDataStore)
    }

    @Test
    fun testChartDataStoreFactoryReturnsCache() {
        assertEquals(chartDataStoreFactory.getCacheDataStore(), chartCacheDataStore)
    }

    @Test
    fun testChartDataStoreFactoryReturnsRemote() {
        assertEquals(chartDataStoreFactory.getRemoteDataStore(), chartRemoteDataStore)
    }

}