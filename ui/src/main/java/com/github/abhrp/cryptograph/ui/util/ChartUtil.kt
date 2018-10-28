package com.github.abhrp.cryptograph.ui.util

import com.anychart.chart.common.dataentry.DataEntry
import com.github.abhrp.cryptograph.domain.executor.PostExecutionThread
import com.github.abhrp.cryptograph.presentation.model.ChartItemView
import com.github.abhrp.cryptograph.ui.mapper.ChartItemMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ChartUtil @Inject constructor(
    private val postExecutionThread: PostExecutionThread,
    private val mapper: ChartItemMapper
) {

    fun getChartData(chartItemViewList: List<ChartItemView>): Single<List<DataEntry>> {
        return Single.just(chartItemViewList)
            .observeOn(Schedulers.computation())
            .subscribeOn(postExecutionThread.scheduler)
            .map { it ->
                it.map {
                    mapper.mapToUiModel(it)
                }
            }
    }

}