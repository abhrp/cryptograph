package com.github.abhrp.cryptograph.remote

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.data.repository.ChartRemote
import com.github.abhrp.cryptograph.remote.mapper.ChartItemMapper
import com.github.abhrp.cryptograph.remote.service.ChartApiService
import io.reactivex.Single
import javax.inject.Inject

class ChartRemoteImpl @Inject constructor(
    private val chartApiService: ChartApiService,
    private val chartItemMapper: ChartItemMapper
) : ChartRemote {

    override fun getChart(timeSpan: String): Single<List<ChartItemEntity>> {
        return chartApiService.getMarketPriceChart(timeSpan).map { response ->
            response.values.map { chartItem ->
                chartItemMapper.mapToEntity(chartItem)
            }
        }
    }

}