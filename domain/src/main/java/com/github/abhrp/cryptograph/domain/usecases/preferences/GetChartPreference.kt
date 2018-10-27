package com.github.abhrp.cryptograph.domain.usecases.preferences

import com.github.abhrp.cryptograph.domain.executor.PostExecutionThread
import com.github.abhrp.cryptograph.domain.model.ChartPreference
import com.github.abhrp.cryptograph.domain.repository.ChartsRepository
import com.github.abhrp.cryptograph.domain.usecases.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetChartPreference @Inject constructor(
    postExecutionThread: PostExecutionThread,
    private val chartsRepository: ChartsRepository
) :
    SingleUseCase<ChartPreference, Nothing>(postExecutionThread) {

    override fun buildUseCaseSingle(params: Nothing?): Single<ChartPreference> = chartsRepository.getChartsPreference()
}