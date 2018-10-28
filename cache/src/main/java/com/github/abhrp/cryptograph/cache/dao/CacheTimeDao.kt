package com.github.abhrp.cryptograph.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.abhrp.cryptograph.cache.constants.CacheConstants
import com.github.abhrp.cryptograph.cache.model.CacheTimeEntity
import io.reactivex.Maybe

@Dao
interface CacheTimeDao {

    @Query(CacheConstants.SELECT_CACHE_TIME)
    fun getCacheTime(timeSpan: String): Maybe<CacheTimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCacheTime(cacheTimeEntity: CacheTimeEntity)

    @Query(CacheConstants.DELETE_CACHE_TIME)
    fun deleteCacheTime(timeSpan: String)

}