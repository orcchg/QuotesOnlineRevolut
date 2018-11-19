package com.orcchg.quotes.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection

abstract class BaseActivity : AppCompatActivity() {

    val vmFactory: ViewModelProvider.Factory by ActivityDelegateVmFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)  // DaggerActivity for AppCompat not available in Jetpack
        super.onCreate(savedInstanceState)
    }
}
