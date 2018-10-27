package com.github.abhrp.cryptograph.domain.factory

import com.github.abhrp.cryptograph.domain.model.ChartItem
import com.github.abhrp.cryptograph.domain.model.ChartPreference

object ChartFactory {

    fun getRandomChartItem(): ChartItem = ChartItem(DataFactory.randomLong(), DataFactory.randomDouble())

    fun getRandomChartItemList(count: Int): List<ChartItem> {
        val chartItems = mutableListOf<ChartItem>()
        repeat(count) {
            chartItems.add(getRandomChartItem())
        }
        return chartItems
    }

    fun getRandomChartPreference(): ChartPreference = ChartPreference(DataFactory.randomString())
}