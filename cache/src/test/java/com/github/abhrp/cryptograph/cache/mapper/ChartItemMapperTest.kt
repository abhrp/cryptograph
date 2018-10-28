package com.github.abhrp.cryptograph.cache.mapper

import com.github.abhrp.cryptograph.cache.factory.ChartCacheFactory
import com.github.abhrp.cryptograph.cache.factory.DataFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ChartItemMapperTest {
    private val chartItemMapper = ChartItemMapper()

    @Test
    fun testChartItemMapperMapsToCacheCorrectly() {
        val chartItemEntity = ChartCacheFactory.getRandomChartItemEntity()
        val timeSpan = DataFactory.randomString()
        val cachedChartItem = chartItemMapper.mapToCache(timeSpan, chartItemEntity)
        assertEquals(cachedChartItem.timeSpan, timeSpan)
        assertEquals(cachedChartItem.dateTime, chartItemEntity.datetime)
        assertEquals(cachedChartItem.value, chartItemEntity.value)
        assertEquals(cachedChartItem.itemId, 1)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMapToCacheThrowsIllegalArgumentExceptionIfTimeSpanIsNull() {
        val chartItemEntity = ChartCacheFactory.getRandomChartItemEntity()
        chartItemMapper.mapToCache(null, chartItemEntity)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMapToCacheThrowsIllegalArgumentExceptionIfTimeSpanIsEmpty() {
        val chartItemEntity = ChartCacheFactory.getRandomChartItemEntity()
        chartItemMapper.mapToCache("", chartItemEntity)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMapToCacheThrowsIllegalArgumentExceptionIfTimeSpanHasWhitespace() {
        val chartItemEntity = ChartCacheFactory.getRandomChartItemEntity()
        chartItemMapper.mapToCache("    ", chartItemEntity)
    }

    @Test
    fun testChartItemMapperMapsToEntityCorrectly() {
        val cachedChartItem = ChartCacheFactory.getRandomCachedChartItem()
        val chartItemEntity = chartItemMapper.mapToEntity(cachedChartItem)
        assertEquals(cachedChartItem.dateTime, chartItemEntity.datetime)
        assertEquals(cachedChartItem.value, chartItemEntity.value)
    }
}