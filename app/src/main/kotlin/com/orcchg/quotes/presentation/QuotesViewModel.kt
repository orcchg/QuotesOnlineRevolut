package com.orcchg.quotes.presentation

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.orcchg.quotes.data.Cloud
import com.orcchg.quotes.domain.Quotes
import com.orcchg.quotes.presentation.adapter.QuoteVO
import com.orcchg.quotes.presentation.adapter.QuotesAdapter
import com.orcchg.quotes.presentation.adapter.QuotesViewHolder
import io.reactivex.disposables.Disposable
import io.reactivex.flowables.ConnectableFlowable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class QuotesViewModel(private val cloud: Cloud) : ViewModel() {

    val adapter = QuotesAdapter(l = {
        base = it.name
        stateMultiplierUpdated(multiplier = 1.0)  // drop multiplier
        source = source()  // set new base to source
        resubscribe()
    }, topItemBound = {
        quantitySubscriber?.dispose()
        quantitySubscriber = QuotesViewHolder.quantityObservable?.subscribe(this::stateMultiplierUpdated, Timber::e)
    })

    private val handler = Handler(Looper.getMainLooper())

    private var base: String = "USD"  // initial base
    private var multiplier: Double = 1.0
    private var source: ConnectableFlowable<Quotes> = source()
    private var firstSubscriber:    Disposable? = null
    private var secondSubscriber:   Disposable? = null
    private var quantitySubscriber: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        clear()
    }

    fun start() {
        firstSubscriber = source.subscribe(this::stateQuotesLoaded, Timber::e)
    }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    private fun clear() {
        firstSubscriber?.dispose()
        secondSubscriber?.dispose()
        quantitySubscriber?.dispose()
    }

    private fun source(): ConnectableFlowable<Quotes> {
        val source = cloud.quotes(base).repeatWhen { it.delay(1, TimeUnit.SECONDS) }.publish()
        source.connect()
        return source
    }

    /**
     * Subscribes on hot observable with quotes and on quantity changes
     */
    private fun resubscribe() {
        secondSubscriber?.dispose()
        secondSubscriber = source.subscribe(this::stateQuotesUpdated, Timber::e)
    }

    // ------------------------------------------
    private fun stateQuotesLoaded(quotes: Quotes) {
        val list = mutableListOf<QuoteVO>()
        list.add(from(key = base, value = 1.0))
        list.addAll(quotes.rates.map { from(key = it.key, value = it.value) })
        adapter.models = list

        firstSubscriber?.dispose()  // unsubscribe from source as list has been filled
        resubscribe()
    }

    private fun stateQuotesUpdated(quotes: Quotes) {
        adapter.apply {
            models.forEach { quotes.rates[it.name]?.apply { it.quantity = this; it.multiplier = multiplier } }
            handler.post { notifyItemRangeChanged(1, models.size - 1) }
        }
    }

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
