package com.github.abhrp.cryptograph.cache.sharedpreferences

import com.github.abhrp.cryptograph.cache.factory.DataFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertEquals
import kotlin.test.assertNull

@RunWith(RobolectricTestRunner::class)
class ChartSharedPreferencesTest {
    private lateinit var chartSharedPreferences: ChartSharedPreferences

    @Before
    fun setup() {
        chartSharedPreferences = ChartSharedPreferences(RuntimeEnvironment.application.applicationContext)
    }

    @Test
    fun testLastTimeSpanIsNullInitially() {
        assertNull(chartSharedPreferences.lastTimeSpanSelection)
    }

    @Test
    fun testLastTimeSpanIsSetCorrectly() {
        val timeSpan = DataFactory.randomString()
        chartSharedPreferences.lastTimeSpanSelection = timeSpan
        assertEquals(chartSharedPreferences.lastTimeSpanSelection, timeSpan)
        val timeSpan2 = DataFactory.randomString()
        chartSharedPreferences.lastTimeSpanSelection = timeSpan2
        assertEquals(chartSharedPreferences.lastTimeSpanSelection, timeSpan2)
        chartSharedPreferences.lastTimeSpanSelection = null
        assertNull(chartSharedPreferences.lastTimeSpanSelection)
    }
}