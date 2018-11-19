package com.orcchg.quotes.di

import androidx.lifecycle.ViewModelProvider
import com.orcchg.quotes.presentation.base.ViewModelFactory
import com.orcchg.quotes.presentation.base.di.ViewModelComponent
import com.orcchg.quotes.presentation.di.QuotesViewModelModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [QuotesViewModelModule::class], subcomponents = [ViewModelComponent::class])
class ApplicationModule {

    @Provides @Singleton
    fun provideViewModelFactory(builder: ViewModelComponent.Builder): ViewModelProvider.Factory =
        ViewModelFactory(builder.build())
}
