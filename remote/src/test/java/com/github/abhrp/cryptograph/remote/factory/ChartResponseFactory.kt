package com.github.abhrp.cryptograph.remote.factory

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.remote.model.ChartItem
import com.github.abhrp.cryptograph.remote.response.MarketPriceChartResponse

object ChartResponseFactory {

    fun getRandomChartItemEntity(): ChartItemEntity =
        ChartItemEntity(DataFactory.randomLong(), DataFactory.randomDouble())

    fun getRandomChartItemEntityList(count: Int): List<ChartItemEntity> {
        val chartItems = mutableListOf<ChartItemEntity>()
        repeat(count) {
            chartItems.add(getRandomChartItemEntity())
        }
        return chartItems
    }

    fun getRandomChartItem(): ChartItem = ChartItem(DataFactory.randomLong(), DataFactory.randomDouble())

    fun getRandomChartItemList(count: Int): List<ChartItem> {
        val chartItemList = mutableListOf<ChartItem>()
        repeat(count) {
            chartItemList.add(getRandomChartItem())
        }
        return chartItemList
    }

    fun getRandomMarketPriceChartResponse(chartItemList: List<ChartItem>): MarketPriceChartResponse {
        return MarketPriceChartResponse(
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            DataFactory.randomString(),
            chartItemList
        )
    }

}