package com.github.abhrp.cryptograph.util

import android.content.Context
import com.github.abhrp.cryptograph.R
import com.github.abhrp.cryptograph.presentation.model.ChartItemView
import com.github.abhrp.cryptograph.ui.mapper.ChartItemMapper
import com.github.abhrp.cryptograph.ui.util.ChartUtil
import com.github.abhrp.cryptograph.ui.util.DateUtil
import com.github.abhrp.cryptograph.ui.util.NumberUtil
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class ChartUtilTest {
    @Mock
    private lateinit var context: Context

    private lateinit var dateUtil: DateUtil
    private lateinit var numberUtil: NumberUtil
    private lateinit var chartItemMapper: ChartItemMapper
    private lateinit var chartUtil: ChartUtil

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        dateUtil = DateUtil(context)
        numberUtil = NumberUtil()
        chartItemMapper = ChartItemMapper(dateUtil, numberUtil)
        chartUtil = ChartUtil(chartItemMapper)
    }

    @Test
    fun testChartUtilReturnsData() {
        val chartItemViews = listOf(ChartItemView(1539993600, 8752.8365))
        stubContextGetString()
        val data = chartUtil.getChartData(chartItemViews)
        assert(data.count() == 1)

    }

    fun stubContextGetString() {
        whenever(context.getString(R.string.date_format)).thenReturn("dd MMMM yyyy")
    }
}