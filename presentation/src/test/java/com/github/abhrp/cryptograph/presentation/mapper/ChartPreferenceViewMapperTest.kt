package com.github.abhrp.cryptograph.presentation.mapper

import com.github.abhrp.cryptograph.presentation.factory.ChartFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ChartPreferenceViewMapperTest {
    private val chartPreferenceViewMapper = ChartPreferenceViewMapper()

    @Test
    fun testMapsToViewCorrectly() {
        val chartPreference = ChartFactory.getRandomChartPreference()
        val chartPreferenceView = chartPreferenceViewMapper.mapToView(chartPreference)
        assertEquals(chartPreferenceView.timeSpan, chartPreference.timeSpan)
    }

    @Test
    fun testMapsToDomainCorrectly() {
        val chartPreferenceView = ChartFactory.getRandomChartPreferenceView()
        val chartPreference = chartPreferenceViewMapper.mapToDomain(chartPreferenceView)

        assertEquals(chartPreferenceView.timeSpan, chartPreference.timeSpan)
    }
}