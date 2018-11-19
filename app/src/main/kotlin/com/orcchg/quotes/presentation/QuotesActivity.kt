package com.orcchg.quotes.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.orcchg.quotes.R
import kotlinx.android.synthetic.main.activity_quotes.*

class QuotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)

        val viewModel = ViewModelProviders.of(this).get(QuotesViewModel::class.java)

        rv_items.apply {
            adapter = viewModel.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
