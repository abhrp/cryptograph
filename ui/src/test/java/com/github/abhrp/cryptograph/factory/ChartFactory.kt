package com.github.abhrp.cryptograph.factory

import com.github.abhrp.cryptograph.presentation.model.ChartItemView
import com.github.abhrp.cryptograph.presentation.model.ChartPreferenceView
import com.github.abhrp.cryptograph.ui.model.ChartItem
import com.github.abhrp.cryptograph.ui.model.ChartPreferenceItem

object ChartFactory {

    fun getRandomChartItemView(): ChartItemView = ChartItemView(DataFactory.randomLong(), DataFactory.randomDouble())

    fun getRandomChartPreferenceView(): ChartPreferenceView = ChartPreferenceView(DataFactory.randomString())

    fun getRandomChartItem(): ChartItem = ChartItem(DataFactory.randomString(), DataFactory.randomDouble())

    fun getChartPreferenceItem(): ChartPreferenceItem =
        ChartPreferenceItem(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomBoolean())
}