package com.github.abhrp.cryptograph.cache.factory

import com.github.abhrp.cryptograph.cache.model.CachedChartItem
import com.github.abhrp.cryptograph.cache.model.LastCacheTime
import com.github.abhrp.cryptograph.data.model.ChartItemEntity

object ChartCacheFactory {

    fun getRandomChartItemEntity(): ChartItemEntity =
        ChartItemEntity(DataFactory.randomLong(), DataFactory.randomDouble())

    fun getRandomCachedChartItem(): CachedChartItem =
        CachedChartItem(1, DataFactory.randomString(), DataFactory.randomLong(), DataFactory.randomDouble())

    fun getRandomLastCacheTime(): LastCacheTime = LastCacheTime(DataFactory.randomString(), DataFactory.randomLong())

    fun getRandomCachedChartItemList(count: Int, timeSpan: String): List<CachedChartItem> {
        val list = mutableListOf<CachedChartItem>()
        repeat(count) {
            val cachedChartItem =
                CachedChartItem(it + 1, timeSpan, DataFactory.randomLong(), DataFactory.randomDouble())
            list.add(cachedChartItem)
        }
        return list
    }

}