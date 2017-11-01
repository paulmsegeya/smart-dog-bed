package com.xavirigau.smartdogbed

import android.app.Activity
import android.os.Bundle
import com.blundell.adc.Ads1015
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : Activity() {

    private lateinit var ads1015: Ads1015
    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ads1015 = Ads1015.Factory().newAds1015(I2C_BUS, I2C_ADDRESS, GAIN)
    }

    override fun onResume() {
        super.onResume()
        disposable = Observable.interval(1, TimeUnit.MINUTES)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ readAdsData() })
    }

    private fun readAdsData() {
        val reading = ads1015.read(Ads1015.Channel.ZERO)
    }

    override fun onPause() {
        disposable?.dispose()
        super.onPause()
    }

    override fun onDestroy() {
        ads1015.close()
        super.onDestroy()
    }

    companion object {
        internal val I2C_BUS = "I2C1"
        internal val I2C_ADDRESS = 0x48
        internal val GAIN = Ads1015.Gain.ONE
    }

}
