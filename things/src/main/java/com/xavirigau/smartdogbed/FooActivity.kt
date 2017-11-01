package com.xavirigau.smartdogbed

import android.app.Activity
import android.os.Bundle
import android.os.SystemClock
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManagerService

class FooActivity : Activity() {

    lateinit var service: PeripheralManagerService
    lateinit var clockPin: Gpio
    lateinit var dataPin: Gpio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = PeripheralManagerService()
    }

    override fun onResume() {
        super.onResume()
        clockPin = service.openGpio(CLOCK)
        dataPin = service.openGpio(DATA)

        clockPin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        clockPin.setActiveType(Gpio.ACTIVE_HIGH)

        dataPin.setDirection(Gpio.DIRECTION_IN)
        dataPin.setActiveType(Gpio.ACTIVE_HIGH)

        waitForConverterDataReady()
        val results = mutableListOf<Int>()
        for (i in 0..20000) {
            val value = readWord()
            results.add(value)
        }
    }

    private fun waitForConverterDataReady() {
        var count = 0
        do {
            SystemClock.sleep(10)
            count++
        } while (dataPin.value && count < 500)

        if (dataPin.value) {
            throw RuntimeException("Timeout waiting for data")
        }
    }

    private fun readWord(): Int {
        var result = 0
        for (i in 0..(COMPARATOR_WORD_LENGTH - 1)) {
            val bitRead = pulseAndReadBit()
            result = (result shl 1) or bitRead
        }
        setGainForNextReading()
        return result
    }

    private fun setGainForNextReading() {
        for (i in 0..GAIN) {
            pulseAndReadBit()
        }
    }

    fun pulseAndReadBit(): Int {
        clockPin.value = true
        clockPin.value = false
        return if (dataPin.value) 1 else 0
    }

    override fun onPause() {
        super.onPause()
        clockPin.close()
        dataPin.close()
    }

    companion object {
        internal val CLOCK = "BCM17"
        internal val DATA = "BCM27"
        internal val COMPARATOR_WORD_LENGTH = 24
        // Channel A Gain: 0 for 128 or 2 for 64 === Channel B Gain: 1 for 32
        internal val GAIN = 0
    }

}
