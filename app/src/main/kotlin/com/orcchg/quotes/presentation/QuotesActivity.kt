package com.orcchg.quotes.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.orcchg.quotes.R

class QuotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)
        val viewModel = ViewModelProviders.of(this).get(QuotesViewModel::class.java)
    }
}
