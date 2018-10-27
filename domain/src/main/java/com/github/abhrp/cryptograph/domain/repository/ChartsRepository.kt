package com.github.abhrp.cryptograph.domain.repository

import com.github.abhrp.cryptograph.domain.model.ChartItem
import com.github.abhrp.cryptograph.domain.model.ChartPreference

import io.reactivex.Single

interface ChartsRepository {
    fun getCharts(timeSpan: String, forceRefresh: Boolean): Single<List<ChartItem>>

    fun getChartsPreference(): Single<ChartPreference>
}