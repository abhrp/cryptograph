package com.github.abhrp.cryptograph.cache.constants

object CacheConstants {
    const val TABLE_CHART_ITEM = "chart_item"
    const val COL_ITEM_ID = "item_id"
    const val COL_TIME_SPAN = "time_span"
    const val COL_DATE_TIME = "datetime"
    const val COL_VALUE = "value"
    const val TABLE_CACHE_TIME = "cache_time"
    const val COL_TIME_STAMP = "time_stamp"

    const val SELECT_ALL_CHART_ITEMS = "select * from $TABLE_CHART_ITEM where $COL_TIME_SPAN=:timeSpan"
    const val DELETE_CHART_ITEMS = "delete from $TABLE_CHART_ITEM where $COL_TIME_SPAN=:timeSpan"

    const val SELECT_CACHE_TIME = "select * from $TABLE_CACHE_TIME where $COL_TIME_SPAN=:timeSpan"
    const val DELETE_CACHE_TIME = "delete from $TABLE_CACHE_TIME where $COL_TIME_SPAN=:timeSpan"
}