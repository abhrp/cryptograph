package com.github.abhrp.cryptograph.ui.di.module

import com.github.abhrp.cryptograph.BuildConfig
import com.github.abhrp.cryptograph.data.repository.ChartRemote
import com.github.abhrp.cryptograph.remote.ChartRemoteImpl
import com.github.abhrp.cryptograph.remote.service.ChartApiService
import com.github.abhrp.cryptograph.remote.service.ChartApiServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RemoteModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun provideChartApiService(): ChartApiService {
            return ChartApiServiceFactory.getChartApiService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindsChartRemote(chartRemoteImpl: ChartRemoteImpl): ChartRemote
}