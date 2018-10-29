package com.github.abhrp.cryptograph.remote.factory

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.remote.model.ChartItem
import com.github.abhrp.cryptograph.remote.response.MarketPriceChartResponse

object ChartResponseFactory {

    fun getRandomChartItemEntity(): ChartItemEntity =
        ChartItemEntity(DataFactory.randomLong(), DataFactory.randomDouble())

    fun getRandomChartItem(): ChartItem = ChartItem(DataFactory.randomLong(), DataFactory.randomDouble())

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