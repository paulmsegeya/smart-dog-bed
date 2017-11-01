package com.xavirigau.smartdogbed

import io.reactivex.Observable

interface ResultReadingService {

    fun read(): Observable<Sleeping>

}
