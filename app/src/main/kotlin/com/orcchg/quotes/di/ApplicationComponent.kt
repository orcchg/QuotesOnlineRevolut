package com.orcchg.quotes.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.orcchg.quotes.QuotesApp
import com.orcchg.quotes.presentation.di.QuotesActivityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class, QuotesActivityModule::class])
interface ApplicationComponent : AndroidInjector<QuotesApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<QuotesApp>() {
        @BindsInstance abstract fun application(app: Application): Builder
    }

    fun vmFactory(): ViewModelProvider.Factory
}
