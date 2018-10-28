package com.github.abhrp.cryptograph.remote.factory

import java.util.*

object DataFactory {
    fun randomString() = UUID.randomUUID().toString()

    fun randomDouble() = Math.random()

    fun randomInt() = (Math.random() * 1000).toInt()

    fun randomLong() = (Math.random() * 1000000).toLong()

    fun randomBoolean() = Math.random() < 0.5
}