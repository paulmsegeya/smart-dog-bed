package com.xavirigau.smartdogbed

import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class FakeWeightReadingService : WeightReadingService {

    override fun startReading(readingFrequencyInMillis: Long): Observable<Result> {
        return Observable.interval(readingFrequencyInMillis, TimeUnit.MILLISECONDS)
                .map { Random().nextBoolean() }
                .map(this::asResult)
                .distinctUntilChanged()
    }

    private fun asResult(random: Boolean): Result = if (random) Idle else Busy

}
