package com.xavirigau.smartdogbed

import com.blundell.adc.Ads1015
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class Ads1015WeightReadingService(
        private val ads1015: Ads1015,
        private val channel: Ads1015.Channel = Ads1015.Channel.ZERO
) : WeightReadingService {

    override fun startReading(readingFrequencyInMillis: Long): Observable<Result> {
        return Observable.interval(readingFrequencyInMillis, TimeUnit.MILLISECONDS)
                .map { ads1015.read(channel) }
                .map(this::asResult)
                .distinctUntilChanged()
    }

    private fun asResult(reading: Int): Result = if (reading < IDLE_THRESHOLD) Busy else Idle

    companion object {
        private const val IDLE_THRESHOLD = 995 // with Gain = 1
    }

}
