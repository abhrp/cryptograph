package com.github.abhrp.cryptograph.ui.util

import android.content.Context
import com.github.abhrp.cryptograph.R
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateUtil @Inject constructor(private val context: Context) {

    fun getDateStringFromUnixDateTime(datetime: Long): String {
        val date = Date(datetime)
        val format = SimpleDateFormat(context.getString(R.string.date_format), Locale.US)
        return format.format(date)
    }

}