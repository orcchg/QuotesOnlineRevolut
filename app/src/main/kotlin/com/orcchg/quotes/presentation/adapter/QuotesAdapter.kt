package com.orcchg.quotes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orcchg.quotes.R

class QuotesAdapter : RecyclerView.Adapter<QuotesViewHolder>() {

    var models: List<QuoteVO> = emptyList()
        set (value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_quote, parent, false)
        return QuotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.bind(model = models[position])
    }

    override fun getItemCount(): Int = models.size
}
