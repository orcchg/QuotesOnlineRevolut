package com.orcchg.quotes.domain

data class Quotes(val base: String, val date: String, val rates: Map<String, Double>)
