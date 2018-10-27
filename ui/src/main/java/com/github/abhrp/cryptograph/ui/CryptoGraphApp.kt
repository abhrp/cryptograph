package com.github.abhrp.cryptograph.ui

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

class CryptoGraphApp : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}