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
                subscriber.dispose()
                subscribeWithBase(base = it.name)
//            stateMultiplierUpdated(multiplier = 1.0)  // drop multiplier
                topUp.onNext(true)
//                quantitySubscriber?.dispose()
//            quantitySubscriber = adapter.quantityObservable?.subscribe(this::stateMultiplierUpdated, Timber::e)
            }
        }
    }

    private var multiplier: Double = 1.0

    private lateinit var subscriber: Disposable
    private var quantitySubscriber: Disposable? = null

    private var isAnimatingListener: (() -> Boolean)? = null

    var topUp: BehaviorSubject<Boolean> = BehaviorSubject.create()
        private set

    override fun onCleared() {
        super.onCleared()
        clear()
    }

    fun start() {
        subscribeWithBase(base = "USD")
    }

    fun setIsAnimatingListener(l: (() -> Boolean)?) {
        isAnimatingListener = l
    }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    private fun clear() {
        adapter.setOnTopItemBound(null)
        subscriber.dispose()
        quantitySubscriber?.dispose()
        topUp.onComplete()
    }

    private fun subscribeWithBase(base: String) {
        subscriber = cloud.quotes(base).repeatWhen { it.delay(1, TimeUnit.SECONDS) }
            .subscribe({ quotes ->
                adapter.apply {
                    if (models.isEmpty()) {
                        val items = mutableListOf<QuoteVO>()
                        items.add(from("USD", quantity = 1.0))
                        items.addAll(quotes.rates.map { from(name = it.key, quantity = it.value, multiplier = multiplier) })
                        models = items
                    } else {
                        models.forEach {
                            quotes.rates[it.name]?.apply {
                                it.quantity = this
                                it.multiplier = multiplier
                            }
                        }
                        notifyItemRangeChanged(1, models.size)
                    }
                }
            }, Timber::e)
    }

    // ------------------------------------------
    private fun stateMultiplierUpdated(multiplier: Double) {
        this.multiplier = multiplier

        adapter.apply {
            for (i in 1 until models.size) {
                models[i].multiplier = multiplier
            }
            notifyItemRangeChanged(1, models.size - 1)
        }
    }
}
