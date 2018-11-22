package com.orcchg.quotes.presentation

import androidx.lifecycle.ViewModel
import com.orcchg.quotes.data.Cloud
import com.orcchg.quotes.presentation.adapter.QuoteVO
import com.orcchg.quotes.presentation.adapter.QuotesAdapter
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import java.util.concurrent.TimeUnit

class QuotesViewModel(private val cloud: Cloud) : ViewModel() {

    val adapter = QuotesAdapter()

    init {
        adapter.apply {
            setHasStableIds(true)
            setOnTopItemBound {
                subscribeWithBase(base = it.name)
                subscribeQuantityUpdates()
                topUp.onNext(true)
            }
        }
    }

    private var quotesSubscriber: Disposable? = null
    private var quantitySubscriber: Disposable? = null

    private var isAnimatingListener: (() -> Boolean)? = null

    var topUp: BehaviorSubject<Boolean> = BehaviorSubject.create()
        private set

    override fun onCleared() {
        super.onCleared()
        adapter.setOnTopItemBound(null)
        quotesSubscriber?.dispose()
        quantitySubscriber?.dispose()
        topUp.onComplete()
    }

    fun start() {
        subscribeWithBase(base = "USD")
    }

    fun setIsAnimatingListener(l: (() -> Boolean)?) {
        isAnimatingListener = l
    }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    private fun subscribeWithBase(base: String) {
        quotesSubscriber?.dispose()
        quotesSubscriber = cloud.quotes(base).repeatWhen { it.delay(1, TimeUnit.SECONDS) }
            .skipWhile { isAnimatingListener?.invoke() == true }
            .subscribe({ quotes ->
                adapter.apply {
                    if (models.isEmpty()) {
                        val items = mutableListOf<QuoteVO>()
                        items.add(from("USD", quantity = 1.0))
                        items.addAll(quotes.rates.map { from(name = it.key, quantity = it.value) })
                        models = items
                    } else {
                        models.forEach {
                            quotes.rates[it.name]?.apply {
                                it.quantity = this
                                it.multiplier = models[9].multiplier
                            }
                        }
                        notifyItemsChanged()
                    }
                }
            }, Timber::e)
    }

    private fun subscribeQuantityUpdates() {
        quantitySubscriber?.dispose()
        quantitySubscriber = adapter.quantityObservable
            ?.subscribe({ multiplier ->
                adapter.apply {
                    for (i in 1 until models.size) {
                        models[i].multiplier = multiplier
                    }
                    notifyItemsChanged()
                }
            }, Timber::e)
    }

    private fun notifyItemsChanged() {
        if (isAnimatingListener?.invoke() != true) {
            adapter.notifyItemRangeChanged(1, adapter.models.size - 1)
        }
    }
}
