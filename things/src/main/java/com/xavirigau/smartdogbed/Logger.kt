package com.xavirigau.smartdogbed

import android.util.Log

interface Logger {

    fun e(message: String, throwable: Throwable)

}

class AndroidLogger : Logger {
    override fun e(message: String, throwable: Throwable) {
        Log.e(TAG, message, throwable)
    }

    companion object {
        private const val TAG = "SmartDogBed"
    }
}
