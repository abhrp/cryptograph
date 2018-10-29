package com.github.abhrp.cryptograph.ui.chart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.abhrp.cryptograph.R
import com.github.abhrp.cryptograph.ui.model.ChartPreferenceItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.time_span_item.*
import javax.inject.Inject

class TimeSpanAdapter @Inject constructor() : RecyclerView.Adapter<TimeSpanAdapter.ViewHolder>() {

    lateinit var list: List<ChartPreferenceItem>
    var chartOptionClickListener: ChartOptionClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.time_span_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    fun selectChartPreference(timeSpan: String) {
        for (item in list) {
            item.isSelected = item.timeSpan == timeSpan
        }
        notifyDataSetChanged()
    }


    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(chartPreferenceItem: ChartPreferenceItem, position: Int) {

            time_span_option.text = chartPreferenceItem.label

            time_span_option.background =
                    if (chartPreferenceItem.isSelected)
                        time_span_option.resources.getDrawable(R.drawable.option_selected_background, null)
                    else
                        time_span_option.resources.getDrawable(R.drawable.option_background, null)

            containerView.setOnClickListener {
                chartOptionClickListener?.onOptionClicked(list[position])
            }
        }
    }
}