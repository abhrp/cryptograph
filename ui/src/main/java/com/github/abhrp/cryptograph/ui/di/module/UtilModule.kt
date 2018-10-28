package com.github.abhrp.cryptograph.ui.di.module

import android.app.Application
import com.github.abhrp.cryptograph.domain.executor.PostExecutionThread
import com.github.abhrp.cryptograph.ui.mapper.ChartItemMapper
import com.github.abhrp.cryptograph.ui.util.ChartUtil
import com.github.abhrp.cryptograph.ui.util.DataUtil
import com.github.abhrp.cryptograph.ui.util.DateUtil
import com.github.abhrp.cryptograph.ui.util.NumberUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilModule {

    @Provides
    @Singleton
    fun getChartUtil(postExecutionThread: PostExecutionThread, chartItemMapper: ChartItemMapper) =
        ChartUtil(postExecutionThread, chartItemMapper)

    @Provides
    @Singleton
    fun getDataUtil(application: Application) = DataUtil(application)

    @Provides
    @Singleton
    fun getDateUtil(application: Application) = DateUtil(application)

    @Provides
    @Singleton
    fun getNumberUtil() = NumberUtil()

}