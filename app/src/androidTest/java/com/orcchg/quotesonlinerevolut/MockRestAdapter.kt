package com.orcchg.quotesonlinerevolut

import com.orcchg.quotes.data.RestAdapter
import com.orcchg.quotes.domain.Quotes
import io.reactivex.Single
import retrofit2.mock.BehaviorDelegate

class MockRestAdapter(private val delegate: BehaviorDelegate<RestAdapter>) : RestAdapter {

    companion object {
        val etalon = Quotes(base = "EUR", date = "2018-09-06",
            rates = mapOf(Pair("AUD", 1.6191), Pair("BGN", 1.9591),  Pair("BRL", 4.7999), Pair("CAD", 1.5364), Pair("CHF", 1.1294),
                Pair("CNY", 7.9585), Pair("CZK", 25.758), Pair("DKK", 7.4692), Pair("GBP", 0.89975), Pair("HKD", 9.1478),
                Pair("HRK", 7.4466), Pair("HUF", 327.04), Pair("IDR", 17353.0), Pair("ILS", 4.1776), Pair("INR", 83.858),
                Pair("ISK", 128.01), Pair("JPY", 129.77), Pair("KRW", 1307.0), Pair("MXN", 22.403), Pair("MYR", 4.8201),
                Pair("NOK", 9.7924), Pair("NZD", 1.7663), Pair("PHP", 62.697), Pair("PLN", 4.3256), Pair("RON", 4.6463),
                Pair("RUB", 79.708), Pair("SEK", 10.609), Pair("SGD", 1.6027), Pair("THB", 38.194), Pair("TRY", 7.641),
                Pair("USD", 1.1654), Pair("ZAR", 17.853)))
    }

    override fun quotes(base: String): Single<Quotes> = delegate.returningResponse(etalon).quotes(base)
}
