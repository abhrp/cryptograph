package com.github.abhrp.cryptograph.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChartItem(@Json(name = "x") val datetime: Long, @Json(name = "y") val value: Double)