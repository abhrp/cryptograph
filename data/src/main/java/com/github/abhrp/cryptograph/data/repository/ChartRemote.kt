package com.github.abhrp.cryptograph.data.repository

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import io.reactivex.Observable

interface ChartRemote {
    fun getChart(timeSpan: String): Observable<List<ChartItemEntity>>
}