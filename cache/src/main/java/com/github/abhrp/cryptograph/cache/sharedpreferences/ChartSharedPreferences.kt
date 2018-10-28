package com.github.abhrp.cryptograph.cache.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class ChartSharedPreferences @Inject constructor(context: Context) {

    companion object {
        private const val PREF_NAME = "com.github.abhrp.cryptograph.cache.preferences"

        private const val PREF_KEY_LAST_TIME_SPAN = "last_time_span"
    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    var lastTimeSpanSelection: String?
        get() = sharedPreferences.getString(PREF_KEY_LAST_TIME_SPAN, null)
        set(lastTimeSpan) {
            sharedPreferences.edit().putString(PREF_KEY_LAST_TIME_SPAN, lastTimeSpan).apply()
        }

}