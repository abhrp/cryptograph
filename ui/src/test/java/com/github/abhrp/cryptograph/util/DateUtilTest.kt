package com.github.abhrp.cryptograph.util

import android.content.Context
import com.github.abhrp.cryptograph.R
import com.github.abhrp.cryptograph.ui.util.DateUtil
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class DateUtilTest {
    @Mock
    private lateinit var context: Context

    private lateinit var dateUtil: DateUtil

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        dateUtil = DateUtil(context)
    }

    @Test
    fun testDateUtilReturnsData() {
        stubContextGetString()
        val dateTime = dateUtil.getDateStringFromUnixDateTime(1539993600000)
        assertEquals("20 October 2018", dateTime)
    }

    fun stubContextGetString() {
        whenever(context.getString(R.string.date_format)).thenReturn("dd MMMM yyyy")
    }
}