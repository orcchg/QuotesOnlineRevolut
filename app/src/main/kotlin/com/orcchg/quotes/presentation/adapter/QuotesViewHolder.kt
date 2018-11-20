package com.orcchg.quotes.presentation.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.widget.textChanges
import com.orcchg.quotes.presentation.DEBOUNCE
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.rv_item_quote.view.*
import java.util.concurrent.TimeUnit

class QuotesViewHolder(view: View, private val l: ((quote: QuoteVO) -> Unit)?,
                       private val topItemBound: (() -> Unit)?) : RecyclerView.ViewHolder(view) {

    companion object {
        var quantityObservable: Observable<Double>? = null
            private set
    }

    fun bind(model: QuoteVO) {
        itemView.apply {
            et_quantity.apply {
                if (adapterPosition == 0) {
                    setText("1.0")  // start from '1.0' quantity when item activated
                    quantityObservable = et_quantity.textChanges().skipInitialValue()
                        .map { if (it.isBlank()) 0.0 else it.toString().toDouble() }
                        .debounce(DEBOUNCE, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    topItemBound?.invoke()
                } else {
                    setText("${model.quantity * model.multiplier}")
                }
            }
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
