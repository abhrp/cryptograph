package com.github.abhrp.cryptograph.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.abhrp.cryptograph.domain.constants.DefaultPreferences
import com.github.abhrp.cryptograph.domain.model.ChartItem
import com.github.abhrp.cryptograph.domain.model.ChartPreference
import com.github.abhrp.cryptograph.domain.usecases.chart.GetChart
import com.github.abhrp.cryptograph.domain.usecases.preferences.GetChartPreference
import com.github.abhrp.cryptograph.domain.usecases.preferences.SetChartPreference
import com.github.abhrp.cryptograph.presentation.mapper.ChartItemViewMapper
import com.github.abhrp.cryptograph.presentation.model.ChartItemView
import com.github.abhrp.cryptograph.presentation.model.ChartPreferenceView
import com.github.abhrp.cryptograph.presentation.state.Resource
import com.github.abhrp.cryptograph.presentation.state.ResourceState
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class ChartPageViewModel @Inject constructor(
    private val getChart: GetChart,
    private val getChartPreference: GetChartPreference,
    private val setChartPreference: SetChartPreference,
    private val chartItemViewMapper: ChartItemViewMapper
) : ViewModel() {
    private val chartLiveData: MutableLiveData<Resource<List<ChartItemView>>> = MutableLiveData()
    private val chartPreferenceLiveData: MutableLiveData<Resource<ChartPreferenceView>> = MutableLiveData()
    private var timeSpan: String = DefaultPreferences.TIME_SPAN

    val lastTimeSpan: String
        get() = timeSpan

    override fun onCleared() {
        getChart.disposeAll()
        getChartPreference.disposeAll()
        setChartPreference.disposeAll()
        timeSpan = ""
        super.onCleared()
    }

    fun triggerChartPageScreen() {
        chartPreferenceLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getChartPreference.execute(GetChartPreferenceSubscriber(), null)
    }

    fun setChartPreference(timeSpan: String) {
        this.timeSpan = timeSpan
        setChartPreference.execute(SetChartPreferenceSubscriber(), SetChartPreference.Params.forTimeSpan(timeSpan))
    }

    fun getChartPreferenceData(): LiveData<Resource<ChartPreferenceView>> = chartPreferenceLiveData

    fun getChartData(): LiveData<Resource<List<ChartItemView>>> = chartLiveData

    fun fetchChartData(timeSpan: String, forceRefresh: Boolean) {
        this.timeSpan = timeSpan
        chartLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        getChart.execute(GetChartSubscriber(), GetChart.Params.forChart(timeSpan, forceRefresh))
    }


    inner class GetChartPreferenceSubscriber : DisposableSingleObserver<ChartPreference>() {
        override fun onSuccess(t: ChartPreference) {
            var timeSpan = DefaultPreferences.TIME_SPAN
            t.timeSpan?.let {
                timeSpan = it
            }
            chartPreferenceLiveData.postValue(Resource(ResourceState.SUCCESS, ChartPreferenceView(timeSpan), null))
        }

        override fun onError(e: Throwable) {
            chartPreferenceLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }

    inner class SetChartPreferenceSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {
            chartPreferenceLiveData.postValue(Resource(ResourceState.NO_CHANGE, ChartPreferenceView(timeSpan), null))
            fetchChartData(timeSpan, false)
        }

        override fun onError(e: Throwable) {
            chartPreferenceLiveData.postValue(
                Resource(
                    ResourceState.ERROR,
                    ChartPreferenceView(timeSpan),
                    e.localizedMessage
                )
            )
        }
    }

    inner class GetChartSubscriber : DisposableSingleObserver<List<ChartItem>>() {
        override fun onSuccess(t: List<ChartItem>) {
            chartLiveData.postValue(Resource(ResourceState.SUCCESS, t.map { chartItemViewMapper.mapToView(it) }, null))
        }

        override fun onError(e: Throwable) {
            chartLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage))
        }

    }
}