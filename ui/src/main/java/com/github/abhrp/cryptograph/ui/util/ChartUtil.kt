package com.github.abhrp.cryptograph.ui.util

import com.anychart.chart.common.dataentry.DataEntry
import com.github.abhrp.cryptograph.presentation.model.ChartItemView
import com.github.abhrp.cryptograph.ui.mapper.ChartItemMapper
import javax.inject.Inject

class ChartUtil @Inject constructor(
    private val mapper: ChartItemMapper
) {

    fun getChartData(chartItemViewList: List<ChartItemView>): List<DataEntry> {
        return chartItemViewList.map {
            mapper.mapToUiModel(it)
        }.toList()
    }
}