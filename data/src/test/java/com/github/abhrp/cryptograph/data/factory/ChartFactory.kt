package com.github.abhrp.cryptograph.data.factory

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import com.github.abhrp.cryptograph.domain.model.ChartItem
import com.github.abhrp.cryptograph.domain.model.ChartPreference

object ChartFactory {
    fun getRandomChartItem(): ChartItem = ChartItem(DataFactory.randomLong(), DataFactory.randomDouble())

    fun getRandomChartPreference(): ChartPreference = ChartPreference(DataFactory.randomString())

    fun getRandomChartItemEntity(): ChartItemEntity =
        ChartItemEntity(DataFactory.randomLong(), DataFactory.randomDouble())

    fun getRandomChartItemEntityList(count: Int): List<ChartItemEntity> {
        val chartItems = mutableListOf<ChartItemEntity>()
        repeat(count) {
            chartItems.add(getRandomChartItemEntity())
        }
        return chartItems
    }

    fun getRandomChartPreferenceEntity(): ChartPreferenceEntity = ChartPreferenceEntity(DataFactory.randomString())
}