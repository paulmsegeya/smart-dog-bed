package com.xavirigau.smartdogbed

import android.app.Activity
import android.os.Bundle
import com.blundell.adc.Ads1015
import com.google.firebase.database.FirebaseDatabase

class MainActivity : Activity() {

    private lateinit var presenter: MainPresenter
//    private lateinit var ads1015: Ads1015

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ads1015 = Ads1015.Factory().newAds1015(I2C_BUS, I2C_ADDRESS, GAIN)
//        presenter = MainPresenter(Ads1015WeightReadingService(ads1015), FirebaseDatabase.getInstance())
        presenter = MainPresenter(FakeWeightReadingService(), FirebaseDatabase.getInstance())
    }

    override fun onResume() {
        super.onResume()
        presenter.startPresenting()
    }

    override fun onPause() {
        presenter.stopPresenting()
        super.onPause()
    }

    override fun onDestroy() {
//        ads1015.close()
        super.onDestroy()
    }

    companion object {
        private const val I2C_BUS = "I2C1"
        private const val I2C_ADDRESS = 0x48
        private val GAIN = Ads1015.Gain.ONE
    }

}
