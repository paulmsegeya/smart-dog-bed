package com.xavirigau.smartdogbed

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(
        private val mainDisplayer: MainDisplayer,
        private val resultReadingService: ResultReadingService,
        private val logger: Logger = AndroidLogger()
) {

    private var disposable: Disposable? = null

    fun startPresenting() {
        disposable = resultReadingService.read()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onResult, this::onError)
    }

    private fun onResult(result: Sleeping) = mainDisplayer.display(result)

    private fun onError(throwable: Throwable) = logger.e("Error while reading data from the weight sensor", throwable)

    fun stopPresenting() {
        disposable?.dispose()
    }

}
