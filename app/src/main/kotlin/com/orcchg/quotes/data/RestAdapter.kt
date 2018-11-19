package com.orcchg.quotes.data

import com.orcchg.quotes.domain.Quotes
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://revolut.duckdns.org/"

interface RestAdapter {

    @GET("latest")
    fun quotes(@Query("base") base: String): Single<Quotes>
}
