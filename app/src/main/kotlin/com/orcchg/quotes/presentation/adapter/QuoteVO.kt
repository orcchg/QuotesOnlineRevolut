package com.orcchg.quotes.presentation.adapter

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class QuoteVO(val name: String, @StringRes val description: Int, @DrawableRes val iconResId: Int, var quantity: Double)
