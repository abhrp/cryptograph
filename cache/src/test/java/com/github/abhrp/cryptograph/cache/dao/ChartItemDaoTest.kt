package com.github.abhrp.cryptograph.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.github.abhrp.cryptograph.cache.factory.ChartCacheFactory
import com.github.abhrp.cryptograph.cache.factory.DataFactory
import com.github.abhrp.cryptograph.cache.sql.ChartDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ChartItemDaoTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ChartDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ChartDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetChartItemsCompletes() {
        val cachedChartItem = ChartCacheFactory.getRandomCachedChartItem()
        val timeSpan = cachedChartItem.timeSpan
        database.getChartItemDao().insertChartItem(listOf(cachedChartItem))
        val testObserver = database.getChartItemDao().getChartItems(timeSpan).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetChartItemsReturnsData() {
        val cachedChartItem = ChartCacheFactory.getRandomCachedChartItem()
        val timeSpan = cachedChartItem.timeSpan
        database.getChartItemDao().insertChartItem(listOf(cachedChartItem))
        val testObserver = database.getChartItemDao().getChartItems(timeSpan).test()
        testObserver.assertValue(listOf(cachedChartItem))
    }

    @Test
    fun testInsertChartItemSavesDataCorrectly() {
        val timeSpan = DataFactory.randomString()
        val chartCacheList = ChartCacheFactory.getRandomCachedChartItemList(100, timeSpan)
        database.getChartItemDao().insertChartItem(chartCacheList)
        val testObserver = database.getChartItemDao().getChartItems(timeSpan).test()
        testObserver.assertValue(chartCacheList)
    }

    @Test
    fun testDeleteChartItemDeletesDataCorrectly() {
        val timeSpan = DataFactory.randomString()
        val chartCacheList = ChartCacheFactory.getRandomCachedChartItemList(100, timeSpan)
        database.getChartItemDao().insertChartItem(chartCacheList)
        val testObserver = database.getChartItemDao().getChartItems(timeSpan).test()
        testObserver.assertValue(chartCacheList)
        database.getChartItemDao().deleteChartItems(timeSpan)
        val testObserver2 = database.getChartItemDao().getChartItems(timeSpan).test()
        testObserver2.assertValue(emptyList())
    }
}