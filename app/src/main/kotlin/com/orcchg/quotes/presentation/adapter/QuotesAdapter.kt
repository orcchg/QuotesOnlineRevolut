package com.orcchg.quotes.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.orcchg.quotes.R

class QuotesAdapter(private val l: ((quote: QuoteVO) -> Unit)?, private val topItemBound: (() -> Unit)?)
    : RecyclerView.Adapter<QuotesViewHolder>() {

    var models: MutableList<QuoteVO> = mutableListOf()
        set (value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_quote, parent, false)
        return QuotesViewHolder(view, l = { quote: QuoteVO ->
            models.find { it.name == quote.name }?.also {
                models.apply {
                    val oldPosition = indexOf(it)
                    if (oldPosition <= 0) {
                        return@apply  // no need to change top item
                    }

                    it.quantity = 1.0  // top item should have '1.0' quantity and multiplier
                    it.multiplier = 1.0
                    removeAt(oldPosition)
                    add(0, it)
                    notifyItemMoved(oldPosition, 0)  // move item to top position
                    notifyItemChanged(0)
                }
            }
            l?.invoke(quote)
        })
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.bind(model = models[position])

        if (position == 0) topItemBound?.invoke()
    }

    override fun getItemCount(): Int = models.size
}
