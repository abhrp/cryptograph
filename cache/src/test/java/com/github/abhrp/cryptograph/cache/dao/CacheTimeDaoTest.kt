package com.github.abhrp.cryptograph.cache.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import com.github.abhrp.cryptograph.cache.factory.ChartCacheFactory
import com.github.abhrp.cryptograph.cache.sql.ChartDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CacheTimeDaoTest {

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
    fun testGetCacheTimeCompletes() {
        val lastCacheTime = ChartCacheFactory.getRandomLastCacheTime()
        val timeSpan = lastCacheTime.timeSpan
        database.getCacheTimeDao().insertCacheTime(lastCacheTime)
        val testObserver = database.getCacheTimeDao().getCacheTime(timeSpan).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetCacheTimeReturnsData() {
        val lastCacheTime = ChartCacheFactory.getRandomLastCacheTime()
        val timeSpan = lastCacheTime.timeSpan
        database.getCacheTimeDao().insertCacheTime(lastCacheTime)
        val testObserver = database.getCacheTimeDao().getCacheTime(timeSpan).test()
        testObserver.assertValue(lastCacheTime)
    }

    @Test
    fun testInsertCacheTimeSavesCorrectly() {
        val lastCacheTime1 = ChartCacheFactory.getRandomLastCacheTime()
        val timeSpan1 = lastCacheTime1.timeSpan
        val lastCacheTime2 = ChartCacheFactory.getRandomLastCacheTime()
        val timeSpan2 = lastCacheTime2.timeSpan
        database.getCacheTimeDao().insertCacheTime(lastCacheTime1)
        database.getCacheTimeDao().insertCacheTime(lastCacheTime2)
        val testObserver1 = database.getCacheTimeDao().getCacheTime(timeSpan1).test()
        testObserver1.assertValue(lastCacheTime1)
        val testObserver2 = database.getCacheTimeDao().getCacheTime(timeSpan2).test()
        testObserver2.assertValue(lastCacheTime2)
    }

    @Test
    fun testDeleteCacheTimeRemovesCorrectly() {
        val lastCacheTime = ChartCacheFactory.getRandomLastCacheTime()
        val timeSpan = lastCacheTime.timeSpan
        database.getCacheTimeDao().insertCacheTime(lastCacheTime)
        val testObserver1 = database.getCacheTimeDao().getCacheTime(timeSpan).test()
        testObserver1.assertValue(lastCacheTime)
        database.getCacheTimeDao().deleteCacheTime(timeSpan)
        val testObserver2 = database.getCacheTimeDao().getCacheTime(timeSpan).test()
        testObserver2.assertNoValues()
    }
}