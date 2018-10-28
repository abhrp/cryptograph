package com.github.abhrp.cryptograph.ui.chart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.abhrp.cryptograph.R
import dagger.android.AndroidInjection

class ChartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_chart)
    }
}
