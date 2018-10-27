package com.github.abhrp.cryptograph.data.store

import com.github.abhrp.cryptograph.data.repository.ChartDataStore
import javax.inject.Inject

class ChartDataStoreFactory @Inject constructor(
    private val chartCacheDataStore: ChartCacheDataStore,
    private val chartRemoteDataStore: ChartRemoteDataStore
) {
    fun getDataStore(chartCached: Boolean, cacheExpired: Boolean, forceRefresh: Boolean): ChartDataStore {
        if (forceRefresh) {
            return chartRemoteDataStore
        }
        return if (chartCached && !cacheExpired) chartCacheDataStore else chartRemoteDataStore
    }

    fun getRemoteDataStore(): ChartRemoteDataStore = chartRemoteDataStore

    fun getCacheDataStore(): ChartCacheDataStore = chartCacheDataStore

}