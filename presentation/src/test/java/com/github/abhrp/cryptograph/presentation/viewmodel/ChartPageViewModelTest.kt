package com.github.abhrp.cryptograph.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.abhrp.cryptograph.domain.model.ChartItem
import com.github.abhrp.cryptograph.domain.model.ChartPreference
import com.github.abhrp.cryptograph.domain.usecases.chart.GetChart
import com.github.abhrp.cryptograph.domain.usecases.preferences.GetChartPreference
import com.github.abhrp.cryptograph.domain.usecases.preferences.SetChartPreference
import com.github.abhrp.cryptograph.presentation.factory.ChartFactory
import com.github.abhrp.cryptograph.presentation.factory.DataFactory
import com.github.abhrp.cryptograph.presentation.mapper.ChartItemViewMapper
import com.github.abhrp.cryptograph.presentation.model.ChartItemView
import com.github.abhrp.cryptograph.presentation.model.ChartPreferenceView
import com.github.abhrp.cryptograph.presentation.state.ResourceState
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ChartPageViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Captor
    private val captor = argumentCaptor<DisposableSingleObserver<List<ChartItem>>>()
    @Captor
    private val captor2 = argumentCaptor<DisposableCompletableObserver>()
    @Captor
    private val captor3 = argumentCaptor<DisposableSingleObserver<ChartPreference>>()

    private val getChart = mock<GetChart>()
    private val setChartPreference = mock<SetChartPreference>()
    private val getChartPreference = mock<GetChartPreference>()

    private val chartItemMapper = mock<ChartItemViewMapper>()

    private val chartPageViewModel =
        ChartPageViewModel(getChart, getChartPreference, setChartPreference, chartItemMapper)

    @Test
    fun testGetChartExecutesUseCase() {
        val timeSpan = DataFactory.randomString()
        chartPageViewModel.fetchChartData(timeSpan, false)
        verify(getChart, times(1)).execute(any(), eq(GetChart.Params(timeSpan, false)))
    }

    @Test
    fun testGetChartReturnsData() {
        val chartItems = listOf(ChartFactory.getRandomChartItem())
        val chartItemViews = listOf(ChartFactory.getRandomChartItemView())
        stubMapper(chartItems[0], chartItemViews[0])
        val timeSpan = DataFactory.randomString()
        chartPageViewModel.fetchChartData(timeSpan, false)
        verify(getChart, times(1)).execute(captor.capture(), eq(GetChart.Params(timeSpan, false)))
        captor.firstValue.onSuccess(chartItems)
        assertEquals(chartItemViews, chartPageViewModel.getChartData().value?.data)
        assertEquals(ResourceState.SUCCESS, chartPageViewModel.getChartData().value?.status)
    }

    @Test
    fun testGetChartReturnsError() {
        val message = DataFactory.randomString()
        chartPageViewModel.fetchChartData("", false)
        verify(getChart).execute(captor.capture(), eq(GetChart.Params("", false)))
        captor.firstValue.onError(RuntimeException(message))
        assertEquals(ResourceState.ERROR, chartPageViewModel.getChartData().value?.status)
        assertEquals(message, chartPageViewModel.getChartData().value?.error)
    }

    @Test
    fun testSetChartPreferenceExecutesUseCase() {
        val timeSpan = DataFactory.randomString()
        chartPageViewModel.setChartPreference(timeSpan)
        verify(setChartPreference, times(1)).execute(any(), eq(SetChartPreference.Params(timeSpan)))
    }

    @Test
    fun testSetChartPreferenceSetsData() {
        val timeSpan = DataFactory.randomString()
        chartPageViewModel.setChartPreference(timeSpan)
        verify(setChartPreference, times(1)).execute(captor2.capture(), eq(SetChartPreference.Params(timeSpan)))
        captor2.firstValue.onComplete()
        assertEquals(ResourceState.NO_CHANGE, chartPageViewModel.getChartPreferenceData().value?.status)
        assertEquals(ChartPreferenceView(timeSpan), chartPageViewModel.getChartPreferenceData().value?.data)
    }

    private fun stubMapper(chartItem: ChartItem, chartItemView: ChartItemView) {
        whenever(chartItemMapper.mapToView(chartItem)).thenReturn(chartItemView)
    }
}