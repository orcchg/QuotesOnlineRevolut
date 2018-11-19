package com.orcchg.quotes.presentation.di

import android.app.Activity
import com.orcchg.quotes.presentation.QuotesActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [QuotesActivityComponent::class])
abstract class QuotesActivityModule {

    @Binds @IntoMap @ActivityKey(QuotesActivity::class)
    abstract fun bind(builder: QuotesActivityComponent.Builder): AndroidInjector.Factory<out Activity>
}
