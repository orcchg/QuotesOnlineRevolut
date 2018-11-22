package com.orcchg.quotes.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.widget.textChanges
import com.orcchg.quotes.R
import com.orcchg.quotes.presentation.DEBOUNCE
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.rv_item_quote.view.*
import java.util.concurrent.TimeUnit

class QuotesAdapter : RecyclerView.Adapter<QuotesViewHolder>() {

    companion object {
        const val VIEW_TYPE_NORMAL = 0
        const val VIEW_TYPE_TOP = 1
    }

    var models: MutableList<QuoteVO> = mutableListOf()
        set (value) {
            val diff = DiffUtil.calculateDiff(QuotesDiffCallback(old = field, new = value))
            field = value
            diff.dispatchUpdatesTo(this)
        }

    private var topItemBound: ((item: QuoteVO) -> Unit)? = null

    var quantityObservable: Observable<Double>? = null
        private set

    fun setOnTopItemBound(topItemBound: ((item: QuoteVO) -> Unit)?) {
        this.topItemBound = topItemBound
    }

    /* Adapter */
    // --------------------------------------------------------------------------------------------
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_quote, parent, false)

        when (viewType) {
            VIEW_TYPE_TOP -> initQuantitySource(view)
        }

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
        })
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.bind(model = models[position])
        if (position == 0) {
            topItemBound?.invoke(models[position])
            initQuantitySource(holder.itemView)
        }
    }

    override fun getItemId(position: Int): Long = models[position].id()

    override fun getItemCount(): Int = models.size

    override fun getItemViewType(position: Int): Int =
            when (position) {
                0 -> VIEW_TYPE_TOP
                else -> VIEW_TYPE_NORMAL
            }

    /* Internal */
    // --------------------------------------------------------------------------------------------
    private fun initQuantitySource(view: View) {
        view.et_quantity.apply {
            quantityObservable = textChanges()
                .debounce(DEBOUNCE, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .map { if (it.isBlank()) 0.0 else it.toString().toDouble() }
                .doOnNext { models[0].multiplier = it }
        }
    }
}
