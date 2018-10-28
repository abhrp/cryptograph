package com.github.abhrp.cryptograph.ui.di.module

import com.github.abhrp.cryptograph.domain.executor.PostExecutionThread
import com.github.abhrp.cryptograph.ui.UIThread
import com.github.abhrp.cryptograph.ui.chart.ChartActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UIThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesChartActivity(): ChartActivity

}