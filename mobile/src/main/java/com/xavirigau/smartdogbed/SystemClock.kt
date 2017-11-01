package com.xavirigau.smartdogbed

class SystemClock : Clock {
    override fun currentTimeInMillis(): Long = System.currentTimeMillis()
}
