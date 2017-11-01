package com.xavirigau.smartdogbed

import com.google.firebase.database.FirebaseDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainPresenter(
        private val weightReadingService: WeightReadingService,
        private val database: FirebaseDatabase,
        private val clock: Clock = SystemClock(),
        private val logger: Logger = AndroidLogger()
) {

    private var disposable: Disposable? = null

    fun startPresenting() {
        disposable = weightReadingService.startReading(READING_FREQUENCY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onResult, this::onError)
    }

    private fun onResult(result: Result) {
        database.getReference("state/${clock.currentTimeInMillis()}").setValue(result.name())
    }

    private fun onError(throwable: Throwable) = logger.e("Error while reading data from the weight sensor", throwable)

    fun stopPresenting() {
        disposable?.dispose()
    }

    companion object {
        private val READING_FREQUENCY = TimeUnit.MINUTES.toMillis(1)
    }

}
