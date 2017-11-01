package com.xavirigau.smartdogbed

import android.util.Log

class AndroidLogger : Logger {
    override fun e(message: String, throwable: Throwable) {
        Log.e(TAG, message, throwable)
    }

    companion object {
        private const val TAG = "SmartDogBed"
    }
}
