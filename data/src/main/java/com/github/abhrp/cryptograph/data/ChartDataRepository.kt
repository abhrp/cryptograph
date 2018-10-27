package com.github.abhrp.cryptograph.data

import com.github.abhrp.cryptograph.data.mapper.ChartItemEntityMapper
import com.github.abhrp.cryptograph.data.mapper.ChartPreferenceEntityMapper
import com.github.abhrp.cryptograph.data.repository.ChartCache
import com.github.abhrp.cryptograph.data.store.ChartDataStoreFactory
import com.github.abhrp.cryptograph.domain.model.ChartItem
import com.github.abhrp.cryptograph.domain.model.ChartPreference
import com.github.abhrp.cryptograph.domain.repository.ChartsRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class ChartDataRepository @Inject constructor(
    private val chartCache: ChartCache,
    private val chartDataStoreFactory: ChartDataStoreFactory,
    private val chartItemEntityMapper: ChartItemEntityMapper,
    private val chartPreferenceEntityMapper: ChartPreferenceEntityMapper
) : ChartsRepository {

    override fun getCharts(timeSpan: String, forceRefresh: Boolean): Observable<List<ChartItem>> {
        return Observable.zip(chartCache.isChartCached(timeSpan).toObservable(),
            chartCache.isCacheExpired(timeSpan).toObservable(),
            BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { isCached, isExpired ->
                Pair(isCached, isExpired)
            })
            .flatMap {
                chartDataStoreFactory.getDataStore(it.first, it.second, forceRefresh).getChart(timeSpan)
            }
            .flatMap { chartItemList ->
                chartDataStoreFactory.getCacheDataStore().saveChart(timeSpan, chartItemList)
                    .andThen(Observable.just(chartItemList))
            }
            .map { chartItemList ->
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