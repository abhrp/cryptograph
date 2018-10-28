package com.github.abhrp.cryptograph.ui.util

import android.content.Context
import com.github.abhrp.cryptograph.R
import com.github.abhrp.cryptograph.ui.model.ChartPreferenceItem
import javax.inject.Inject

class DataUtil @Inject constructor(private val context: Context) {

    fun getChartSpanList(): List<ChartPreferenceItem> {
        return listOf(
            ChartPreferenceItem(
                context.getString(R.string.one_week),
                context.getString(R.string.one_week_label),
                false
            ),
            ChartPreferenceItem(
                context.getString(R.string.one_month),
                context.getString(R.string.one_month_label),
                false
            ),
            ChartPreferenceItem(
                context.getString(R.string.three_months),
                context.getString(R.string.three_months_label),
                false
            ),
            ChartPreferenceItem(
                context.getString(R.string.six_months),
                context.getString(R.string.six_months_label),
                false
            ),
            ChartPreferenceItem(
                context.getString(R.string.twelve_months),
                context.getString(R.string.one_year_label),
                false
            ),
            ChartPreferenceItem(
                context.getString(R.string.five_years),
                context.getString(R.string.five_years_label),
                false
            )
        )
    }

}