package com.github.abhrp.cryptograph.remote.mapper

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.remote.model.ChartItem

class ChartItemMapper : ModelMapper<ChartItem, ChartItemEntity> {

    override fun mapToEntity(model: ChartItem): ChartItemEntity = ChartItemEntity(model.datetime, model.value)

}