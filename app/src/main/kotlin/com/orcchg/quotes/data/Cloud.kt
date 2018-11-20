package com.orcchg.quotes.data

import com.orcchg.quotes.domain.Quotes
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Singleton
class Cloud(private val restAdapter: RestAdapter) {

    fun quotes(base: String = "USD"): Flowable<Quotes> =
        restAdapter.quotes(base = base)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable()
}
