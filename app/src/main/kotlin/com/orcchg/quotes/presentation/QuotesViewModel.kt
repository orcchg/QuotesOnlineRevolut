package com.orcchg.quotes.presentation

import androidx.lifecycle.ViewModel
import com.orcchg.quotes.data.Cloud
import com.orcchg.quotes.presentation.adapter.QuotesAdapter

class QuotesViewModel(cloud: Cloud) : ViewModel() {

    val adapter = QuotesAdapter()
}
