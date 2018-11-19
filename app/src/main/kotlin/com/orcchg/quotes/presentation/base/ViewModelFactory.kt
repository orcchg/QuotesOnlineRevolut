package com.orcchg.quotes.presentation.base

import android.util.ArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.orcchg.quotes.presentation.QuotesViewModel
import com.orcchg.quotes.presentation.base.di.ViewModelComponent
import javax.inject.Singleton

@Singleton
class ViewModelFactory(component: ViewModelComponent) : ViewModelProvider.Factory {

    private val creators = ArrayMap<Class<*>, ViewModel>()

    init {
        creators[QuotesViewModel::class.java] = component.quotesViewModel()
    }

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        creators.entries.forEach {
            if (modelClass.isAssignableFrom(it.key)) {
                return it.value as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
