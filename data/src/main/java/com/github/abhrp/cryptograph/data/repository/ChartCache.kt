package com.github.abhrp.cryptograph.data.repository

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ChartCache {
    fun getChart(timeSpan: String): Observable<List<ChartItemEntity>>
    fun getChartPreference(): Single<ChartPreferenceEntity>
    fun clearChart(timeSpan: String): Completable
    fun saveChart(timeSpan: String, chartData: List<ChartItemEntity>): Completable
    fun isChartCached(timeSpan: String): Single<Boolean>
    fun setLastCacheTime(timeSpan: String): Completable
    fun isCacheExpired(timeSpan: String): Single<Boolean>
}