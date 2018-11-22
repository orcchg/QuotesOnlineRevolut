package com.orcchg.quotes.presentation.adapter

import androidx.recyclerview.widget.DiffUtil

class QuotesDiffCallback(val old: List<QuoteVO>, val new: List<QuoteVO>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size
    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition].id() == new[newItemPosition].id()

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        old[oldItemPosition] == new[newItemPosition]
}
