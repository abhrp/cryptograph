package com.github.abhrp.cryptograph.data.mapper

import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import com.github.abhrp.cryptograph.domain.model.ChartItem
import javax.inject.Inject

class ChartItemEntityMapper @Inject constructor() : EntityMapper<ChartItemEntity, ChartItem> {

    override fun mapToDomain(entity: ChartItemEntity): ChartItem = ChartItem(entity.datetime, entity.value)

    override fun mapToEntity(domain: ChartItem): ChartItemEntity = ChartItemEntity(domain.datetime, domain.value)

}