package com.github.abhrp.cryptograph.data.store

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import com.github.abhrp.cryptograph.data.repository.ChartCache
import com.github.abhrp.cryptograph.data.repository.ChartDataStore
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ChartCacheDataStore @Inject constructor(private val chartCache: ChartCache) : ChartDataStore {

    override fun getChart(timeSpan: String): Single<List<ChartItemEntity>> = chartCache.getChart(timeSpan)

    override fun setChartPreference(chartPreferenceEntity: ChartPreferenceEntity): Completable =
        chartCache.setChartPreference(chartPreferenceEntity)

    override fun getChartPreference(): Single<ChartPreferenceEntity> = chartCache.getChartPreference()

    override fun clearChart(timeSpan: String): Completable = chartCache.clearChart(timeSpan)

    override fun saveChart(timeSpan: String, chartData: List<ChartItemEntity>, saveTimestamp: Long): Completable =
        chartCache.saveChart(timeSpan, chartData)
            .andThen(chartCache.setLastCacheTime(timeSpan, saveTimestamp))

}