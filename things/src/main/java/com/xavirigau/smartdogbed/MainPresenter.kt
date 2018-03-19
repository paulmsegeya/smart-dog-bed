package com.xavirigau.smartdogbed

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainPresenter(
        private val weightReadingService: WeightReadingService,
        private val resultStoringService: ResultStoringService,
        private val logger: Logger = AndroidLogger()
) {

    private var disposable: Disposable? = null

    fun startPresenting() {
        disposable = weightReadingService.startReading(READING_FREQUENCY)
                .map(resultStoringService::store)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(onNext = {}, onError = this::onError)
    }

    private fun onError(throwable: Throwable) = logger.e("Error while reading data from the weight sensor", throwable)

    fun stopPresenting() {
        disposable?.dispose()
    }

    companion object {
        private val READING_FREQUENCY = TimeUnit.MINUTES.toMillis(1)
//        private val READING_FREQUENCY = TimeUnit.SECONDS.toMillis(5) // for testing
    }

}
