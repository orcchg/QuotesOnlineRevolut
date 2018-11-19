package com.orcchg.quotes.presentation.base

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import com.orcchg.quotes.QuotesApp
import com.orcchg.quotes.di.ApplicationComponent
import kotlin.reflect.KProperty

class ActivityDelegateVmFactory {

    operator fun getValue(thisRef: Any, property: KProperty<*>): ViewModelProvider.Factory {
        val application = (thisRef as Activity).application as QuotesApp
        val component = application.applicationInjector() as ApplicationComponent
        return component.vmFactory()
    }
}
