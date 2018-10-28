package com.github.abhrp.cryptograph.ui.mapper

import com.github.abhrp.cryptograph.presentation.model.ChartItemView
import com.github.abhrp.cryptograph.ui.model.ChartItem
import com.github.abhrp.cryptograph.ui.util.DateUtil
import com.github.abhrp.cryptograph.ui.util.NumberUtil
import javax.inject.Inject

class ChartItemMapper @Inject constructor(
    private val dateUtil: DateUtil,
    private val numberUtil: NumberUtil
) : UIMapper<ChartItemView, ChartItem> {

    override fun mapToUiModel(view: ChartItemView): ChartItem {
        return ChartItem(
            dateUtil.getDateStringFromUnixDateTime(view.datetime * 1000),
            numberUtil.roundToDecimals(view.value, 2)
        )
    }

}