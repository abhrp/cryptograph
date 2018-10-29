package com.github.abhrp.cryptograph.ui.di.module

import android.app.Application
import com.github.abhrp.cryptograph.ui.mapper.ChartItemMapper
import com.github.abhrp.cryptograph.ui.util.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule {

    @Provides
    @Singleton
    fun getChartUtil(chartItemMapper: ChartItemMapper) =
        ChartUtil(chartItemMapper)

    @Provides
    @Singleton
    fun getDataUtil(application: Application) = DataUtil(application)

    @Provides
    @Singleton
    fun getDateUtil(application: Application) = DateUtil(application)

    @Provides
    @Singleton
    fun getNumberUtil() = NumberUtil()

    @Provides
    @Singleton
    fun getConnectivityUtil(application: Application) = ConnectivityUtil(application)

}