package com.github.abhrp.cryptograph.presentation.mapper

import com.github.abhrp.cryptograph.domain.model.ChartItem
import com.github.abhrp.cryptograph.presentation.model.ChartItemView
import javax.inject.Inject

class ChartItemViewMapper @Inject constructor() : ViewMapper<ChartItemView, ChartItem> {
    override fun mapToView(domain: ChartItem): ChartItemView = ChartItemView(domain.datetime, domain.value)

    override fun mapToDomain(view: ChartItemView): ChartItem = ChartItem(view.datetime, view.value)
}