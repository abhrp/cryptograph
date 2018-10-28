package com.github.abhrp.cryptograph.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.abhrp.cryptograph.cache.constants.CacheConstants

@Entity(tableName = CacheConstants.TABLE_CACHE_TIME)
data class CacheTimeEntity(
    @PrimaryKey
    @ColumnInfo(name = CacheConstants.COL_TIME_SPAN)
    val timeSpan: String,
    @ColumnInfo(name = CacheConstants.COL_TIME_STAMP)
    val timestamp: Long
)