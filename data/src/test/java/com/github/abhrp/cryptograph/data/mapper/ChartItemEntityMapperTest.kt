package com.github.abhrp.cryptograph.data.mapper

import com.github.abhrp.cryptograph.data.factory.ChartFactory
import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.domain.model.ChartItem
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ChartItemEntityMapperTest {
    private val chartItemEntityMapper = ChartItemEntityMapper()

    @Test
    fun testChartItemEntityMapperMapsToEntityCorrectly() {
        val chartItem = ChartFactory.getRandomChartItem()
        val chartItemEntity = chartItemEntityMapper.mapToEntity(chartItem)
        assertEqualData(chartItem, chartItemEntity)
    }

    @Test
    fun testChartItemEntityMapperMapsToDomainCorrectly() {
        val chartItemEntity = ChartFactory.getRandomChartItemEntity()
        val chartItem = chartItemEntityMapper.mapToDomain(chartItemEntity)
        assertEqualData(chartItem, chartItemEntity)
    }

    private fun assertEqualData(chartItem: ChartItem, chartItemEntity: ChartItemEntity) {
        assertEquals(chartItem.datetime, chartItemEntity.datetime)
        assertEquals(chartItem.value, chartItemEntity.value)
    }
}