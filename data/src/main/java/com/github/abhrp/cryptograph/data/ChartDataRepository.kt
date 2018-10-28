package com.github.abhrp.cryptograph.data

import com.github.abhrp.cryptograph.data.mapper.ChartItemEntityMapper
import com.github.abhrp.cryptograph.data.mapper.ChartPreferenceEntityMapper
import com.github.abhrp.cryptograph.data.repository.ChartCache
import com.github.abhrp.cryptograph.data.repository.ChartDataStore
import com.github.abhrp.cryptograph.data.store.ChartDataStoreFactory
import com.github.abhrp.cryptograph.data.store.ChartRemoteDataStore
import com.github.abhrp.cryptograph.domain.model.ChartItem
import com.github.abhrp.cryptograph.domain.model.ChartPreference
import com.github.abhrp.cryptograph.domain.repository.ChartsRepository
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ChartDataRepository @Inject constructor(
    private val chartCache: ChartCache,
    private val chartDataStoreFactory: ChartDataStoreFactory,
    private val chartItemEntityMapper: ChartItemEntityMapper,
    private val chartPreferenceEntityMapper: ChartPreferenceEntityMapper
) : ChartsRepository {

    override fun getCharts(timeSpan: String, forceRefresh: Boolean): Single<List<ChartItem>> {
        return Single.zip(
            chartCache.isChartCached(timeSpan),
            chartCache.isCacheExpired(timeSpan),
            BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { isCached, isExpired ->
                Pair(isCached, isExpired)
            })
            .flatMap {
                val dataStore = chartDataStoreFactory.getDataStore(it.first, it.second, forceRefresh)
                getChartFromStore(dataStore, timeSpan)
            }
    }

    private fun getChartFromStore(chartDataStore: ChartDataStore, timeSpan: String): Single<List<ChartItem>> {
        return chartDataStore.getChart(timeSpan)
            .flatMap { chartItemList ->
                if (chartDataStore is ChartRemoteDataStore) {
                    chartDataStoreFactory.getCacheDataStore().clearChart(timeSpan)
                        .andThen(chartDataStoreFactory.getCacheDataStore().saveChart(timeSpan, chartItemList))
                        .andThen(Single.just(chartItemList))
                } else {
                    Single.just(chartItemList)
                }
            }.map { chartItemList ->
                chartItemList.map {
                    chartItemEntityMapper.mapToDomain(it)
                }
            }
    }

    override fun getChartsPreference(): Single<ChartPreference> {
        return chartDataStoreFactory.getCacheDataStore().getChartPreference().map {
            chartPreferenceEntityMapper.mapToDomain(it)
        }
    }

}