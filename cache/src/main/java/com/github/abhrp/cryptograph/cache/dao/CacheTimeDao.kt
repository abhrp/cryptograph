package com.github.abhrp.cryptograph.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.abhrp.cryptograph.cache.constants.CacheConstants
import com.github.abhrp.cryptograph.cache.model.LastCacheTime
import io.reactivex.Maybe

@Dao
interface CacheTimeDao {

    @Query(CacheConstants.SELECT_CACHE_TIME)
    fun getCacheTime(timeSpan: String): Maybe<LastCacheTime>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCacheTime(lastCacheTime: LastCacheTime)

    @Query(CacheConstants.DELETE_CACHE_TIME)
    fun deleteCacheTime(timeSpan: String)

}