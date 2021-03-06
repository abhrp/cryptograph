package com.github.abhrp.cryptograph.cache

import com.github.abhrp.cryptograph.cache.mapper.ChartItemMapper
import com.github.abhrp.cryptograph.cache.model.LastCacheTime
import com.github.abhrp.cryptograph.cache.sharedpreferences.ChartSharedPreferences
import com.github.abhrp.cryptograph.cache.sql.ChartDatabase
import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import com.github.abhrp.cryptograph.data.repository.ChartCache
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ChartCacheImpl @Inject constructor(
    private val chartDatabase: ChartDatabase,
    private val chartSharedPreferences: ChartSharedPreferences,
    private val chartItemMapper: ChartItemMapper
) : ChartCache {

    override fun getChart(timeSpan: String): Single<List<ChartItemEntity>> {
        return chartDatabase.getChartItemDao()
            .getChartItems(timeSpan)
            .toSingle()
            .map { cachedItem ->
                cachedItem.map {
                    chartItemMapper.mapToEntity(it)
                }
            }
    }

    override fun setChartPreference(chartPreferenceEntity: ChartPreferenceEntity): Completable {
        return Completable.defer {
            chartSharedPreferences.lastTimeSpanSelection = chartPreferenceEntity.timeSpan
            Completable.complete()
        }
    }

    override fun getChartPreference(): Single<ChartPreferenceEntity> {
        val chartPreferenceEntity = ChartPreferenceEntity(chartSharedPreferences.lastTimeSpanSelection)
        return Single.just(chartPreferenceEntity)
    }

    override fun clearChart(timeSpan: String): Completable {
        return Completable.defer {
            chartDatabase.getChartItemDao().deleteChartItems(timeSpan)
            Completable.complete()
        }
    }

    override fun saveChart(timeSpan: String, chartData: List<ChartItemEntity>): Completable {
        return Completable.defer {
            chartDatabase.getChartItemDao().insertChartItem(chartData.map { chartItem ->
                chartItemMapper.mapToCache(timeSpan, chartItem)
            })
            Completable.complete()
        }
    }

    override fun isChartCached(timeSpan: String): Single<Boolean> {
        return chartDatabase.getChartItemDao().getChartItems(timeSpan).isEmpty.map {
            !it
        }
    }

    override fun setLastCacheTime(timeSpan: String, lastCacheTime: Long): Completable {
        return Completable.defer {
            val cacheTimeEntity = LastCacheTime(timeSpan, lastCacheTime)
            chartDatabase.getCacheTimeDao().insertCacheTime(cacheTimeEntity)
            Completable.complete()
        }
    }


    override fun isCacheExpired(timeSpan: String): Single<Boolean> {
        return chartDatabase.getCacheTimeDao().getCacheTime(timeSpan).toSingle().map {
            isExpired(it)
        }.onErrorReturn {
            true
        }
    }

    private fun isExpired(lastCacheTime: LastCacheTime): Boolean {
        val cacheTime = lastCacheTime.timestamp
        val currentTime = System.currentTimeMillis()
        val expirationTime = (60 * 9 * 1000).toLong()
        return currentTime - cacheTime > expirationTime
    }
}