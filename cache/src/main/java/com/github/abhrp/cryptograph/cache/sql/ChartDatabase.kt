package com.github.abhrp.cryptograph.cache.sql

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.abhrp.cryptograph.cache.dao.CacheTimeDao
import com.github.abhrp.cryptograph.cache.dao.ChartItemDao
import com.github.abhrp.cryptograph.cache.model.CacheTimeEntity
import com.github.abhrp.cryptograph.cache.model.CachedChartItem

@Database(entities = arrayOf(CachedChartItem::class, CacheTimeEntity::class), version = 1)
abstract class ChartDatabase : RoomDatabase() {
    abstract fun getChartItemDao(): ChartItemDao
    abstract fun getCacheTimeDao(): CacheTimeDao

    companion object {
        private var INSTANCE: ChartDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): ChartDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE =
                                Room.databaseBuilder(context.applicationContext, ChartDatabase::class.java, "chart.db")
                                    .build()
                    }
                    return INSTANCE as ChartDatabase
                }
            }
            return INSTANCE as ChartDatabase
        }
    }
}