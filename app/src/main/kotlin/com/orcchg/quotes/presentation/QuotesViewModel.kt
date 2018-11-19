package com.orcchg.quotes.presentation

import androidx.lifecycle.ViewModel
import com.orcchg.quotes.presentation.adapter.QuotesAdapter

class QuotesViewModel : ViewModel() {

    val adapter = QuotesAdapter()
}
