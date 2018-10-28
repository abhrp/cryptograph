package com.github.abhrp.cryptograph.remote.service

import com.github.abhrp.cryptograph.remote.constants.APIConstants
import com.github.abhrp.cryptograph.remote.response.MarketPriceChartResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ChartApiService {
    @GET(APIConstants.CHARTS_API)
    fun getMarketPriceChart(@Query(APIConstants.TIME_SPAN_PARAM) timeSpan: String, @Query(APIConstants.SAMPLED_PARAM) isSampled: Boolean = APIConstants.IS_SAMPLED): Single<MarketPriceChartResponse>
}