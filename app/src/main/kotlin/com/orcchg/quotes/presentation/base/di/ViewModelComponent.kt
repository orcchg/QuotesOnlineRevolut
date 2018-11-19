package com.orcchg.quotes.presentation.base.di

import com.orcchg.quotes.presentation.QuotesViewModel
import dagger.Subcomponent

@Subcomponent
interface ViewModelComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelComponent
    }

    fun quotesViewModel(): QuotesViewModel
}
