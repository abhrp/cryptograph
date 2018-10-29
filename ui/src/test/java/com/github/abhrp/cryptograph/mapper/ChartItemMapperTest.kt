package com.github.abhrp.cryptograph.mapper

import android.content.Context
import com.github.abhrp.cryptograph.R
import com.github.abhrp.cryptograph.presentation.model.ChartItemView
import com.github.abhrp.cryptograph.ui.mapper.ChartItemMapper
import com.github.abhrp.cryptograph.ui.util.DateUtil
import com.github.abhrp.cryptograph.ui.util.NumberUtil
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ChartItemMapperTest {

    @Mock
    private lateinit var context: Context

    private lateinit var dateUtil: DateUtil
    private lateinit var numberUtil: NumberUtil
    private lateinit var chartItemMapper: ChartItemMapper

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        dateUtil = DateUtil(context)
        numberUtil = NumberUtil()
        chartItemMapper = ChartItemMapper(dateUtil, numberUtil)
    }

    @Test
    fun testChartItemMapperMapsCorrectly() {
        stubContextGetString()
        val chartItemView = ChartItemView(1539993600, 8752.8365)
        val chartItem = chartItemMapper.mapToUiModel(chartItemView)
        assertEquals(chartItem.value, 8752.84)
        assertEquals(chartItem.date, "20 October 2018")
    }

    fun stubContextGetString() {
        whenever(context.getString(R.string.date_format)).thenReturn("dd MMMM yyyy")
    }

}