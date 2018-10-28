package com.github.abhrp.cryptograph.domain.usecases.preferences

import com.github.abhrp.cryptograph.domain.executor.PostExecutionThread
import com.github.abhrp.cryptograph.domain.model.ChartPreference
import com.github.abhrp.cryptograph.domain.repository.ChartsRepository
import com.github.abhrp.cryptograph.domain.usecases.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class SetChartPreference @Inject constructor(
    postExecutionThread: PostExecutionThread,
    private val chartsRepository: ChartsRepository
) : CompletableUseCase<SetChartPreference.Params>(postExecutionThread) {
    override fun buildUseCaseCompletable(params: Params?): Completable {
        params?.let {
            if (it.timeSpan.trim() == "") {
                throw IllegalArgumentException("Timespan cannot be empty")
            }
            return chartsRepository.setChartsPreference(ChartPreference(params.timeSpan))
        }
        throw IllegalArgumentException("Params cannot be null")
    }

    data class Params constructor(val timeSpan: String) {
        companion object {
            fun forTimeSpan(timeSpan: String) = Params(timeSpan)
        }
    }
}