package com.github.abhrp.cryptograph.data.repository

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import io.reactivex.Single

interface ChartRemote {
    fun getChart(timeSpan: String): Single<List<ChartItemEntity>>
}