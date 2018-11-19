package com.orcchg.quotes.presentation.di

import com.orcchg.quotes.data.Cloud
import com.orcchg.quotes.data.di.CloudModule
import com.orcchg.quotes.presentation.QuotesViewModel
import dagger.Module
import dagger.Provides

@Module(includes = [CloudModule::class])
class QuotesViewModelModule {

    @Provides
    fun provideQuotesViewModel(cloud: Cloud): QuotesViewModel = QuotesViewModel(cloud)
}
