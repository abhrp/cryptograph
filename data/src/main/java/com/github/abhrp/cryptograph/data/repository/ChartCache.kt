package com.github.abhrp.cryptograph.data.repository

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import io.reactivex.Completable
import io.reactivex.Single

interface ChartCache {
    fun getChart(timeSpan: String): Single<List<ChartItemEntity>>
    fun getChartPreference(): Single<ChartPreferenceEntity>
    fun clearChart(timeSpan: String): Completable
    fun saveChart(timeSpan: String, chartData: List<ChartItemEntity>): Completable
    fun isChartCached(timeSpan: String): Single<Boolean>
    fun setLastCacheTime(timeSpan: String, lastCacheTime: Long): Completable
    fun isCacheExpired(timeSpan: String): Single<Boolean>
}