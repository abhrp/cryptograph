package com.github.abhrp.cryptograph.presentation.mapper

import com.github.abhrp.cryptograph.domain.model.ChartPreference
import com.github.abhrp.cryptograph.presentation.model.ChartPreferenceView
import javax.inject.Inject

class ChartPreferenceViewMapper @Inject constructor() : ViewMapper<ChartPreferenceView, ChartPreference> {
    override fun mapToView(domain: ChartPreference): ChartPreferenceView = ChartPreferenceView(domain.timeSpan)

    override fun mapToDomain(view: ChartPreferenceView): ChartPreference = ChartPreference(view.timeSpan)
}