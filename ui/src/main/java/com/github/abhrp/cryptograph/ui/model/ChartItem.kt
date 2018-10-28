package com.github.abhrp.cryptograph.ui.model

import com.anychart.chart.common.dataentry.ValueDataEntry

data class ChartItem(val date: String, val value: Double) : ValueDataEntry(date, value)