package com.github.abhrp.cryptograph.ui.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.abhrp.cryptograph.presentation.viewmodel.ChartPageViewModel
import com.github.abhrp.cryptograph.ui.di.ViewModelFactory
import com.github.abhrp.cryptograph.ui.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(ChartPageViewModel::class)
    abstract fun bindsChartPageViewModel(chartPageViewModel: ChartPageViewModel): ViewModel

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}