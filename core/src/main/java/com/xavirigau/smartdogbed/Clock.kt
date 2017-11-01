package com.xavirigau.smartdogbed

import java.util.*

interface Clock {

    fun currentTimeInMillis(): Long

}

fun Long.toDate(): Date = Date(this)
