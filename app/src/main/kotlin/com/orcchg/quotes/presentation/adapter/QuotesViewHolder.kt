package com.orcchg.quotes.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_item_quote.view.*

class QuotesViewHolder(view: View, private val l: ((quote: QuoteVO) -> Unit)?) : RecyclerView.ViewHolder(view) {

    fun bind(model: QuoteVO) {
        itemView.apply {
            et_quantity.setText("${model.quantity}")
            iv_icon.setImageResource(model.iconResId)
            tv_quote_description.setText(model.description)
            tv_quote_title.text = model.name
            vg_root.setOnClickListener {
                et_quantity.apply {
                    setSelection(et_quantity.text.length)
                    requestFocus()
                }
                l?.invoke(model)
            }
        }
    }
}
