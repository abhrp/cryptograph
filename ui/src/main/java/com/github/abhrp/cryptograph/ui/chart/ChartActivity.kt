package com.github.abhrp.cryptograph.ui.chart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.CrosshairDisplayMode
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.github.abhrp.cryptograph.R
import com.github.abhrp.cryptograph.presentation.state.ResourceState
import com.github.abhrp.cryptograph.presentation.viewmodel.ChartPageViewModel
import com.github.abhrp.cryptograph.ui.di.ViewModelFactory
import com.github.abhrp.cryptograph.ui.model.ChartPreferenceItem
import com.github.abhrp.cryptograph.ui.util.ChartUtil
import com.github.abhrp.cryptograph.ui.util.ConnectivityUtil
import com.github.abhrp.cryptograph.ui.util.DataUtil
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_chart.*
import javax.inject.Inject

class ChartActivity : AppCompatActivity(), ChartOptionClickListener {

    @Inject
    lateinit var chartPageViewModel: ChartPageViewModel
    @Inject
    lateinit var chartUtil: ChartUtil
    @Inject
    lateinit var viemodelFactory: ViewModelFactory
    @Inject
    lateinit var adapter: TimeSpanAdapter
    @Inject
    lateinit var dataUtil: DataUtil
    @Inject
    lateinit var connectivityUtil: ConnectivityUtil

    private lateinit var set: Set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_chart)
        chartPageViewModel = ViewModelProviders.of(this, viemodelFactory).get(ChartPageViewModel::class.java)

        setUpOptionsList()
        setUpSwipeToRefreshView()

        initChartWidget()

        getLastOrDefaultPreference()
        getChartData()
    }

    override fun onResume() {
        super.onResume()
        if (connectivityUtil.isConnectedToNetwork()) {
            startLoad()
        } else {
            showOfflineMessage()
        }
    }

    private fun setUpSwipeToRefreshView() {
        swipe_to_refresh_layout.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        swipe_to_refresh_layout.setOnRefreshListener {
            if (connectivityUtil.isConnectedToNetwork()) {
                fetchChartData(chartPageViewModel.lastTimeSpan, true)
            } else {
                swipe_to_refresh_layout.isRefreshing = false
                showOfflineMessage()
            }
        }
    }

    private fun setUpOptionsList() {
        time_span_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter.chartOptionClickListener = this
        adapter.list = dataUtil.getChartSpanList()
        time_span_list.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun startLoad() {
        chartPageViewModel.triggerChartPageScreen()
    }

    private fun getLastOrDefaultPreference() {
        chartPageViewModel.getChartPreferenceData().observe(this, Observer { resource ->
            when (resource.status) {
                ResourceState.LOADING -> {
                    progressBarVisibility(true)
                }
                ResourceState.SUCCESS -> {
                    progressBarVisibility(false)
                    resource.data?.let { chartPreferenceView ->
                        chartPreferenceView.timeSpan?.let {
                            adapter.selectChartPreference(it)
                            fetchChartData(it, false)
                        }
                    }
                }
                ResourceState.ERROR -> {
                    progressBarVisibility(false)
                    showError()
                }
                ResourceState.NO_CHANGE -> {
                    progressBarVisibility(false)
                }
            }
        })
    }

    private fun getChartData() {
        chartPageViewModel.getChartData().observe(this, Observer { resources ->
            when (resources.status) {
                ResourceState.LOADING -> {
                    progressBarVisibility(true)
                }
                ResourceState.SUCCESS -> {
                    progressBarVisibility(false)
                    resources.data?.let { chartItemViewList ->
                        refreshChartData(chartUtil.getChartData(chartItemViewList))
                    }
                }
                ResourceState.ERROR -> {
                    progressBarVisibility(false)
                    showError()
                }
                ResourceState.NO_CHANGE -> {
                    progressBarVisibility(false)
                }
            }
        })
    }

    private fun fetchChartData(timeSpan: String, forceRefresh: Boolean) {
        chartPageViewModel.fetchChartData(timeSpan, forceRefresh)
    }

    private fun setChartPreference(timeSpan: String) {
        if (connectivityUtil.isConnectedToNetwork()) {
            progressBarVisibility(true)
            chartPageViewModel.setChartPreference(timeSpan)
        } else {
            showOfflineMessage()
        }

    }

    private fun showOfflineMessage() {
        showError(getString(R.string.offline_message))
    }

    private fun progressBarVisibility(visible: Boolean) {
        progress_bar.visibility = if (visible) View.VISIBLE else View.GONE
        swipe_to_refresh_layout.isRefreshing = false
    }

    private fun initChartWidget() {
        chart_view.setZoomEnabled(true)
        val cartesian = AnyChart.cartesian()
        cartesian.removeAllSeries()
        cartesian.animation(true)
        cartesian.padding(10.0, 20.0, 5.0, 20.0)
        cartesian.crosshair().enabled(true)
        cartesian.crosshair().displayMode(CrosshairDisplayMode.STICKY)
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.title(getString(R.string.chart_title))

        cartesian.yAxis(0).title("USD")
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)
        set = Set.instantiate()
        val seriesMapping = set.mapAs("{ x: 'date', value: 'value'}")
        val series = cartesian.line(seriesMapping)
        series.name(getString(R.string.line_series_name))
        series.hovered().markers().enabled(true)
        series.hovered().markers().type(MarkerType.CIRCLE).size(4.0)
        series.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5.0).offsetY(5.0)
        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)
        chart_view.setChart(cartesian)
    }

    private fun refreshChartData(list: List<DataEntry>) {
        set.data(list)
    }

    override fun onOptionClicked(chartPreferenceItem: ChartPreferenceItem) {
        if (connectivityUtil.isConnectedToNetwork()) {
            adapter.selectChartPreference(chartPreferenceItem.timeSpan)
            setChartPreference(chartPreferenceItem.timeSpan)
        } else {
            showOfflineMessage()
        }
    }

    private fun showError(msg: String? = "") {
        Toast.makeText(this, msg ?: getString(R.string.error_msg), Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.chartOptionClickListener = null
    }
}
