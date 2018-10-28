package com.github.abhrp.cryptograph.ui.util

import javax.inject.Inject


class NumberUtil @Inject constructor() {

    fun roundToDecimals(number: Double, numDecimalPlaces: Int): Double {
        val factor = Math.pow(10.0, numDecimalPlaces.toDouble())
        return Math.round(number * factor) / factor
    }

}