package com.github.abhrp.cryptograph.data.mapper

import com.github.abhrp.cryptograph.data.model.ChartPreferenceEntity
import com.github.abhrp.cryptograph.domain.model.ChartPreference
import javax.inject.Inject

class ChartPreferenceEntityMapper @Inject constructor() : EntityMapper<ChartPreferenceEntity, ChartPreference> {
    override fun mapToDomain(entity: ChartPreferenceEntity): ChartPreference = ChartPreference(entity.timeSpan)

    override fun mapToEntity(domain: ChartPreference): ChartPreferenceEntity = ChartPreferenceEntity(domain.timeSpan)
}