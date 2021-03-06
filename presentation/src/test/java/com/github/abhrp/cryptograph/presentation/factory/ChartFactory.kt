package com.github.abhrp.cryptograph.presentation.factory

import com.github.abhrp.cryptograph.domain.model.ChartItem
import com.github.abhrp.cryptograph.domain.model.ChartPreference
import com.github.abhrp.cryptograph.presentation.model.ChartItemView
import com.github.abhrp.cryptograph.presentation.model.ChartPreferenceView

object ChartFactory {
    fun getRandomChartItem(): ChartItem = ChartItem(DataFactory.randomLong(), DataFactory.randomDouble())

    fun getRandomChartPreference(): ChartPreference = ChartPreference(DataFactory.randomString())

    fun getRandomChartItemView(): ChartItemView = ChartItemView(DataFactory.randomLong(), DataFactory.randomDouble())

    fun getRandomChartPreferenceView(): ChartPreferenceView = ChartPreferenceView(DataFactory.randomString())
}