package com.orcchg.quotes

import com.orcchg.quotes.di.ApplicationComponent
import com.orcchg.quotes.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

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
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return packageName + ":" + super.createStackElementTag(element) + ":" + element.lineNumber
                }
            })
        }
    }
}
