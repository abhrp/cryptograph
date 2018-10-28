package com.github.abhrp.cryptograph.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.abhrp.cryptograph.cache.constants.CacheConstants
import com.github.abhrp.cryptograph.cache.model.CachedChartItem
import io.reactivex.Maybe

@Dao
interface ChartItemDao {

    @Query(CacheConstants.SELECT_ALL_CHART_ITEMS)
    fun getChartItems(timeSpan: String): Maybe<List<CachedChartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChartItem(cachedChartItemList: List<CachedChartItem>)

    @Query(CacheConstants.DELETE_CHART_ITEMS)
    fun deleteChartItems(timeSpan: String)

}