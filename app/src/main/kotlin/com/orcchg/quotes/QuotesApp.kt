package com.orcchg.quotes

import com.orcchg.quotes.di.ApplicationComponent
import com.orcchg.quotes.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class QuotesApp : DaggerApplication() {

    private var appComponent: ApplicationComponent? = null

    public override fun applicationInjector(): AndroidInjector<QuotesApp> {
        if (appComponent == null) {
            appComponent = DaggerApplicationComponent.builder()
                .application(this)
                .create(this) as ApplicationComponent
        }

        return appComponent!!
    }

    override fun onCreate() {
        super.onCreate()
    }
}
