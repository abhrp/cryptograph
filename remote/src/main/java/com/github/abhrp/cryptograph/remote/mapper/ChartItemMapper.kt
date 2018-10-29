package com.github.abhrp.cryptograph.remote.mapper

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.remote.model.ChartItem
import javax.inject.Inject

class ChartItemMapper @Inject constructor() : ModelMapper<ChartItem, ChartItemEntity> {

    override fun mapToEntity(model: ChartItem): ChartItemEntity = ChartItemEntity(model.datetime, model.value)

}