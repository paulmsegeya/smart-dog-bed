package com.xavirigau.smartdogbed

interface Clock {

    fun currentTimeInMillis(): Long

}

class SystemClock : Clock {
    override fun currentTimeInMillis(): Long = System.currentTimeMillis()
}
