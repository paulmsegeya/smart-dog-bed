package com.xavirigau.smartdogbed

interface Logger {

    fun w(message: String)

    fun e(message: String, throwable: Throwable)

}

