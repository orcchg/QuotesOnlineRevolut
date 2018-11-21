package com.orcchg.quotes.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.orcchg.quotes.R
import com.orcchg.quotes.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_quotes.*

class QuotesActivity : BaseActivity() {
    
    private lateinit var viewModel: QuotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)

        viewModel = ViewModelProviders.of(this, vmFactory).get(QuotesViewModel::class.java)

        rv_items.apply {
            adapter = viewModel.adapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        viewModel.apply {
            setOnItemTopUp { rv_items.post { rv_items.scrollToPosition(0) } }  // scroll list to top position
            setIsAnimatingListener { rv_items.isAnimating }
            start()  // start fetching data
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        viewModel.apply {
            setOnItemTopUp(null)
            setIsAnimatingListener(null)
        }
    }
}
