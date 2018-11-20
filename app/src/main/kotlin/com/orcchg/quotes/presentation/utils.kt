package com.orcchg.quotes.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.orcchg.quotes.R

@StringRes
fun getDescription(key: String): Int =
    when (key) {
        "AUD" -> R.string.description_aud
        "BGN" -> R.string.description_bgn
        "BRL" -> R.string.description_brl
        "CAD" -> R.string.description_cad
        "CHF" -> R.string.description_chf
        "CNY" -> R.string.description_cny
        "CZK" -> R.string.description_czk
        "DKK" -> R.string.description_dkk
        "GBP" -> R.string.description_gbp
        "HKD" -> R.string.description_hkd
        "HRK" -> R.string.description_hrk
        "HUF" -> R.string.description_huf
        "IDR" -> R.string.description_idr
        "ILS" -> R.string.description_ils
        "INR" -> R.string.description_inr
        "ISK" -> R.string.description_isk
        "JPY" -> R.string.description_jpy
        "KRW" -> R.string.description_krw
        "MXN" -> R.string.description_mxn
        "MYR" -> R.string.description_myr
        "NOK" -> R.string.description_nok
        "NZD" -> R.string.description_nzd
        "PHP" -> R.string.description_php
        "PLN" -> R.string.description_pln
        "RON" -> R.string.description_ron
        "RUB" -> R.string.description_rub
        "SEK" -> R.string.description_sek
        "SGD" -> R.string.description_sgd
        "THB" -> R.string.description_thb
        "TRY" -> R.string.description_try
        "ZAR" -> R.string.description_zar
        "EUR" -> R.string.description_eur
        "USD" -> R.string.description_usd
        else -> R.string.description_usd
    }

@DrawableRes
fun getIcon(key: String): Int =
    when (key) {
        "AUD" -> R.drawable.ic_country_flag_au
        "BGN" -> R.drawable.ic_country_flag_bg
        "BRL" -> R.drawable.ic_country_flag_br
        "CAD" -> R.drawable.ic_country_flag_ca
        "CHF" -> R.drawable.ic_country_flag_ch
        "CNY" -> R.drawable.ic_country_flag_cn
        "CZK" -> R.drawable.ic_country_flag_cz
        "DKK" -> R.drawable.ic_country_flag_dk
        "GBP" -> R.drawable.ic_country_flag_gb
        "HKD" -> R.drawable.ic_country_flag_hk
        "HRK" -> R.drawable.ic_country_flag_hr
        "HUF" -> R.drawable.ic_country_flag_hu
        "IDR" -> R.drawable.ic_country_flag_id
        "ILS" -> R.drawable.ic_country_flag_il
        "INR" -> R.drawable.ic_country_flag_in
        "ISK" -> R.drawable.ic_country_flag_is
        "JPY" -> R.drawable.ic_country_flag_jp
        "KRW" -> R.drawable.ic_country_flag_kr
        "MXN" -> R.drawable.ic_country_flag_mx
        "MYR" -> R.drawable.ic_country_flag_my
        "NOK" -> R.drawable.ic_country_flag_no
        "NZD" -> R.drawable.ic_country_flag_nz
        "PHP" -> R.drawable.ic_country_flag_ph
        "PLN" -> R.drawable.ic_country_flag_pl
        "RON" -> R.drawable.ic_country_flag_ro
        "RUB" -> R.drawable.ic_country_flag_ru
        "SEK" -> R.drawable.ic_country_flag_se
        "SGB" -> R.drawable.ic_country_flag_sg
        "THB" -> R.drawable.ic_country_flag_th
        "TRY" -> R.drawable.ic_country_flag_tr
        "ZAR" -> R.drawable.ic_country_flag_za
        "EUR" -> R.drawable.ic_country_flag_eu
        "USD" -> R.drawable.ic_country_flag_us
        else -> R.drawable.ic_country_flag_ww
    }
