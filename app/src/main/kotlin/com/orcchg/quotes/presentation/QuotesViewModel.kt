package com.orcchg.quotes.presentation

import androidx.lifecycle.ViewModel
import com.orcchg.quotes.data.Cloud
import com.orcchg.quotes.domain.Quotes
import com.orcchg.quotes.presentation.adapter.QuotesAdapter
import io.reactivex.disposables.Disposable
import timber.log.Timber

class QuotesViewModel(private val cloud: Cloud) : ViewModel() {

    val adapter = QuotesAdapter()
    private var disposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        clear()
    }

    fun quotes(base: String = "USD") {
        clear()
        disposable = cloud.quotes(base).subscribe(this::stateQuotesLoaded, Timber::e)
    }

    private fun clear() {
        disposable?.dispose()
    }

    private fun stateQuotesLoaded(quotes: Quotes) {
//        adapter.models = quotes.rates.map {}
    }
}
