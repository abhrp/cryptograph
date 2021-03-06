package com.github.abhrp.cryptograph.cache.mapper

import com.github.abhrp.cryptograph.cache.model.CachedChartItem
import com.github.abhrp.cryptograph.data.model.ChartItemEntity
import javax.inject.Inject

class ChartItemMapper @Inject constructor() : CacheMapper<String, CachedChartItem, ChartItemEntity> {

    override fun mapToCache(param: String?, entity: ChartItemEntity): CachedChartItem {
        param?.let {
            return if (it.trim() != "" && it.isNotEmpty()) CachedChartItem(
                0,
                param,
                entity.datetime,
                entity.value
            ) else throw java.lang.IllegalArgumentException("Param cannot be empty")
        }
        throw IllegalArgumentException("Param cannot be null")
    }

    override fun mapToEntity(cache: CachedChartItem): ChartItemEntity {
        return ChartItemEntity(cache.dateTime, cache.value)
    }
}