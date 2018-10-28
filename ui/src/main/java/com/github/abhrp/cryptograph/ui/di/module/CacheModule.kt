package com.github.abhrp.cryptograph.ui.di.module

import android.app.Application
import com.github.abhrp.cryptograph.cache.ChartCacheImpl
import com.github.abhrp.cryptograph.cache.sql.ChartDatabase
import com.github.abhrp.cryptograph.data.repository.ChartCache
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class CacheModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun providesDatabase(application: Application): ChartDatabase {
            return ChartDatabase.getInstance(application)
        }
    }

    @Binds
    abstract fun bindsChartCache(chartCacheImpl: ChartCacheImpl): ChartCache
}