package com.github.abhrp.cryptograph.remote.mapper

import com.github.abhrp.cryptograph.remote.factory.ChartResponseFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ChartItemMapperTest {
    private val chartItemMapper = ChartItemMapper()

    @Test
    fun testChartItemMapperMapsToEntityCorrectly() {
        val chartItem = ChartResponseFactory.getRandomChartItem()
        val chartItemEntity = chartItemMapper.mapToEntity(chartItem)
        assertEquals(chartItem.datetime, chartItemEntity.datetime)
        assertEquals(chartItem.value, chartItemEntity.value)
    }
}