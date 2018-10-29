package com.github.abhrp.cryptograph.ui.chart

import com.github.abhrp.cryptograph.ui.model.ChartPreferenceItem

interface ChartOptionClickListener {
    fun onOptionClicked(chartPreferenceItem: ChartPreferenceItem)
}