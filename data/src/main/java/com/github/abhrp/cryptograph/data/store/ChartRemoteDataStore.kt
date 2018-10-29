package com.github.abhrp.cryptograph.data.store

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import com.github.abhrp.cryptograph.data.repository.ChartDataStore
import com.github.abhrp.cryptograph.data.repository.ChartRemote
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ChartRemoteDataStore @Inject constructor(private val chartRemote: ChartRemote) : ChartDataStore {

    override fun getChart(timeSpan: String): Single<List<ChartItemEntity>> = chartRemote.getChart(timeSpan)

    override fun getChartPreference(): Single<ChartPreferenceEntity> =
        throw UnsupportedOperationException("Not allowed on remote")

    override fun clearChart(timeSpan: String): Completable =
        throw UnsupportedOperationException("Not allowed on remote")

    override fun saveChart(
        timeSpan: String,
        chartData: List<ChartItemEntity>,
        saveTimestamp: Long
    ): Completable =
        throw UnsupportedOperationException("Not allowed on remote")

    override fun setChartPreference(chartPreferenceEntity: ChartPreferenceEntity): Completable =
        throw UnsupportedOperationException("Not allowed on remote")
}