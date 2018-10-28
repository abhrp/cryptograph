package com.github.abhrp.cryptograph.ui.di.module

import com.github.abhrp.cryptograph.data.ChartDataRepository
import com.github.abhrp.cryptograph.domain.repository.ChartsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindsChartDataRepository(chartDataRepository: ChartDataRepository): ChartsRepository
}