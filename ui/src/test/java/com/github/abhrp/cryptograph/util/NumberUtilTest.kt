package com.github.abhrp.cryptograph.util

import com.github.abhrp.cryptograph.ui.util.NumberUtil
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class NumberUtilTest {
    private val numberUtil = NumberUtil()

    @Test
    fun testNumberUtilReturnsData() {
        assertEquals(345.56, numberUtil.roundToDecimals(345.5623413131, 2))
        assertEquals(5667.652, numberUtil.roundToDecimals(5667.651736373, 3))
    }
}