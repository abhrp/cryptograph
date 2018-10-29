package com.github.abhrp.cryptograph.presentation.mapper

import com.github.abhrp.cryptograph.presentation.factory.ChartFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ChartItemViewMapperTest {
    private val chartItemViewMapper = ChartItemViewMapper()

    @Test
    fun testMapsToViewCorrectly() {
        val chartItem = ChartFactory.getRandomChartItem()
        val chartItemView = chartItemViewMapper.mapToView(chartItem)
        assertEquals(chartItemView.datetime, chartItem.datetime)
        assertEquals(chartItemView.value, chartItem.value)
    }

    @Test
    fun testMapsToDomainCorrectly() {
        val chartItemView = ChartFactory.getRandomChartItemView()
        val chartItem = chartItemViewMapper.mapToDomain(chartItemView)
        assertEquals(chartItemView.datetime, chartItem.datetime)
        assertEquals(chartItemView.value, chartItem.value)
    }
}