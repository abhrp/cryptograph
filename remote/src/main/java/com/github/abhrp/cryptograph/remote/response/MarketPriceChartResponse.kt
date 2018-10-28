package com.github.abhrp.cryptograph.remote.response

import com.github.abhrp.cryptograph.remote.model.ChartItem
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarketPriceChartResponse(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<ChartItem>
)