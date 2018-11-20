package com.orcchg.quotes.presentation

import androidx.lifecycle.ViewModel
import com.orcchg.quotes.data.Cloud
import com.orcchg.quotes.domain.Quotes
import com.orcchg.quotes.presentation.adapter.QuoteVO
import com.orcchg.quotes.presentation.adapter.QuotesAdapter
import io.reactivex.disposables.Disposable
import io.reactivex.flowables.ConnectableFlowable
import timber.log.Timber
import java.util.concurrent.TimeUnit

class QuotesViewModel(private val cloud: Cloud) : ViewModel() {

    val adapter = QuotesAdapter {
        base = it.name
        source = source()  // set new base
        secondSubscriber?.dispose()
        secondSubscriber = source.subscribe(this::stateQuotesUpdated, Timber::e)
    }

    private var base: String = "USD"  // initial base
    private var source: ConnectableFlowable<Quotes> = source()
    private var firstSubscriber:  Disposable? = null
    private var secondSubscriber: Disposable? = null

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
    }

    private fun source(): ConnectableFlowable<Quotes> {
        val source = cloud.quotes(base).repeatWhen { it.delay(1, TimeUnit.SECONDS) }.publish()
        source.connect()
        return source
    }

    // ------------------------------------------
    private fun stateQuotesLoaded(quotes: Quotes) {
        val list = mutableListOf<QuoteVO>()
        list.add(from(key = base, value = 1.0))
        list.addAll(quotes.rates.map { from(key = it.key, value = it.value) })
        adapter.models = list

        firstSubscriber?.dispose()  // unsubscribe from source as list has been filled
        secondSubscriber = source.subscribe(this::stateQuotesUpdated, Timber::e)
    }

    private fun stateQuotesUpdated(quotes: Quotes) {
        adapter.apply {
            models.forEach { quotes.rates[it.name]?.apply { it.quantity = this } }
            notifyDataSetChanged()
        }
    }
}
