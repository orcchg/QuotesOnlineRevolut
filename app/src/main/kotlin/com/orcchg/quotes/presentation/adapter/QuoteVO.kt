package com.orcchg.quotes.presentation.adapter

import androidx.annotation.DrawableRes

data class QuoteVO(val name: String, val description: String, @DrawableRes val iconResId: Int, val quantity: Double)
