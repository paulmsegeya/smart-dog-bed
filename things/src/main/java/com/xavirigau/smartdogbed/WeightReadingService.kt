package com.xavirigau.smartdogbed

import io.reactivex.Observable

interface WeightReadingService {

    fun startReading(readingFrequencyInMillis: Long): Observable<Result>

}
