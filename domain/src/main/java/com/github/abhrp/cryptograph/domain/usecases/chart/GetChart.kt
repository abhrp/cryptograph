package com.github.abhrp.cryptograph.domain.usecases.chart

import com.github.abhrp.cryptograph.domain.executor.PostExecutionThread
import com.github.abhrp.cryptograph.domain.model.ChartItem
import com.github.abhrp.cryptograph.domain.repository.ChartsRepository
import com.github.abhrp.cryptograph.domain.usecases.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class GetChart @Inject constructor(
    postExecutionThread: PostExecutionThread,
    private val chartsRepository: ChartsRepository
) :
    ObservableUseCase<List<ChartItem>, GetChart.Params>(postExecutionThread) {


    override fun buildUseCaseObservable(params: Params?): Observable<List<ChartItem>> {
        params?.let {
            return chartsRepository.getCharts(it.timeSpan, it.forceRefresh)
        }
        throw IllegalArgumentException("Params cannot be null")
    }

    data class Params constructor(val timeSpan: String, val forceRefresh: Boolean) {
        companion object {
            fun forChart(timeSpan: String, forceRefresh: Boolean) = Params(timeSpan, forceRefresh)
        }
    }
}