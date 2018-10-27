package com.github.abhrp.cryptograph.data.mapper

import com.github.abhrp.cryptograph.data.factory.ChartFactory
import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import com.github.abhrp.cryptograph.domain.model.ChartPreference
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ChartPreferenceEntityMapperTest {
    private val chartPreferenceEntityMapper = ChartPreferenceEntityMapper()

    private fun assertEqualData(chartPreference: ChartPreference, chartPreferenceEntity: ChartPreferenceEntity) {
        assertEquals(chartPreference.timeSpan, chartPreferenceEntity.timeSpan)
    }

    @Test
    fun testChartPreferenceEntityMapperMapsToEntityCorrectly() {
        val chartPreference = ChartFactory.getRandomChartPreference()
        val chartPreferenceEntity = chartPreferenceEntityMapper.mapToEntity(chartPreference)
        assertEqualData(chartPreference, chartPreferenceEntity)
    }

    @Test
    fun testChartPreferenceEntityMapperMapsToDomainCorrectly() {
        val chartPreferenceEntity = ChartFactory.getRandomChartPreferenceEntity()
        val chartPreference = chartPreferenceEntityMapper.mapToDomain(chartPreferenceEntity)
        assertEqualData(chartPreference, chartPreferenceEntity)
    }
}