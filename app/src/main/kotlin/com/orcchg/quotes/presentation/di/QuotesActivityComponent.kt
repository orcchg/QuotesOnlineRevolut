package com.orcchg.quotes.presentation.di

import com.orcchg.quotes.presentation.QuotesActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent
interface QuotesActivityComponent : AndroidInjector<QuotesActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<QuotesActivity>()
}
