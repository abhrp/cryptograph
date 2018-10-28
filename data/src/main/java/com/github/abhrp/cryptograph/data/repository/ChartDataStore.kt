package com.github.abhrp.cryptograph.data.repository

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import io.reactivex.Completable
import io.reactivex.Single


interface ChartDataStore {

    fun getChart(timeSpan: String): Single<List<ChartItemEntity>>
    fun setChartPreference(chartPreferenceEntity: ChartPreferenceEntity): Completable
    fun getChartPreference(): Single<ChartPreferenceEntity>
    fun clearChart(timeSpan: String): Completable
    fun saveChart(timeSpan: String, chartData: List<ChartItemEntity>): Completable

}