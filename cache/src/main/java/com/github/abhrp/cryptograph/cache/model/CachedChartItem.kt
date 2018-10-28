package com.github.abhrp.cryptograph.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.abhrp.cryptograph.cache.constants.CacheConstants

@Entity(tableName = CacheConstants.TABLE_CHART_ITEM)
data class CachedChartItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CacheConstants.COL_ITEM_ID)
    val itemId: Int = 0,
    @ColumnInfo(name = CacheConstants.COL_TIME_SPAN)
    val timeSpan: String,
    @ColumnInfo(name = CacheConstants.COL_DATE_TIME)
    val dateTime: Long,
    @ColumnInfo(name = CacheConstants.COL_VALUE)
    val value: Double
)