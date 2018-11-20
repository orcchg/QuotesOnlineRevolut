package com.orcchg.quotes.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.widget.textChanges
import com.orcchg.quotes.presentation.DEBOUNCE
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.rv_item_quote.view.*
import java.util.concurrent.TimeUnit

class QuotesViewHolder(view: View, private val l: ((quote: QuoteVO) -> Unit)?) : RecyclerView.ViewHolder(view) {

    companion object {
        var quantityObservable: Observable<Double>? = null
            private set
    }

    fun bind(model: QuoteVO) {
        val clickListener = View.OnClickListener {
                itemView.et_quantity.apply {
                    setSelection(et_quantity.text.length)
                    requestFocus()
                }
                l?.invoke(model)
        }

        itemView.apply {
            et_quantity.apply {
                tag = adapterPosition
                setText("${model.quantity * model.multiplier}")
                if (adapterPosition == 0) {
                    quantityObservable = et_quantity.textChanges().skipInitialValue()
                        .skipWhile { et_quantity.tag as Int != 0 }
                        .map { if (it.isBlank()) 0.0 else it.toString().toDouble() }
                        .debounce(DEBOUNCE, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                }
                setOnClickListener(clickListener)
            }
            iv_icon.setImageResource(model.iconResId)
            tv_quote_description.setText(model.description)
            tv_quote_title.text = model.name
            vg_root.setOnClickListener(clickListener)
        }
    }
}
