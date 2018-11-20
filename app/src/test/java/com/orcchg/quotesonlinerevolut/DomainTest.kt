package com.orcchg.quotesonlinerevolut

import com.google.gson.GsonBuilder
import com.orcchg.quotes.domain.Quotes
import com.orcchg.quotes.domain.QuotesDeserializer
import org.junit.Assert
import org.junit.Test

class DomainTest {

    val gson = GsonBuilder()
        .registerTypeAdapter(Quotes::class.java, QuotesDeserializer())
        .setDateFormat("yyyy-MM-dd")
        .create()

    @Test
    fun testDeserialization_someValidJson_validModel() {
        val json = "{\"base\":\"EUR\",\"date\":\"2018-09-06\"," +
                    "\"rates\":{" +
                        "\"AUD\":1.6191,\"BGN\":1.9591,\"BRL\":4.7999,\"CAD\":1.5364,\"CHF\":1.1294," +
                        "\"CNY\":7.9585,\"CZK\":25.758,\"DKK\":7.4692,\"GBP\":0.89975,\"HKD\":9.1478," +
                        "\"HRK\":7.4466,\"HUF\":327.04,\"IDR\":17353.0,\"ILS\":4.1776,\"INR\":83.858," +
                        "\"ISK\":128.01,\"JPY\":129.77,\"KRW\":1307.0,\"MXN\":22.403,\"MYR\":4.8201," +
                        "\"NOK\":9.7924,\"NZD\":1.7663,\"PHP\":62.697,\"PLN\":4.3256,\"RON\":4.6463," +
                        "\"RUB\":79.708,\"SEK\":10.609,\"SGD\":1.6027,\"THB\":38.194,\"TRY\":7.641," +
                        "\"USD\":1.1654,\"ZAR\":17.853" +
                        "}" +
                    "}"
        val etalon = Quotes(base = "EUR", date = "2018-09-06",
            rates = mapOf(Pair("AUD", 1.6191), Pair("BGN", 1.9591),  Pair("BRL", 4.7999), Pair("CAD", 1.5364), Pair("CHF", 1.1294),
                 Pair("CNY", 7.9585), Pair("CZK", 25.758), Pair("DKK", 7.4692), Pair("GBP", 0.89975), Pair("HKD", 9.1478),
                 Pair("HRK", 7.4466), Pair("HUF", 327.04), Pair("IDR", 17353.0), Pair("ILS", 4.1776), Pair("INR", 83.858),
                 Pair("ISK", 128.01), Pair("JPY", 129.77), Pair("KRW", 1307.0), Pair("MXN", 22.403), Pair("MYR", 4.8201),
                 Pair("NOK", 9.7924), Pair("NZD", 1.7663), Pair("PHP", 62.697), Pair("PLN", 4.3256), Pair("RON", 4.6463),
                 Pair("RUB", 79.708), Pair("SEK", 10.609), Pair("SGD", 1.6027), Pair("THB", 38.194), Pair("TRY", 7.641),
                 Pair("USD", 1.1654), Pair("ZAR", 17.853)))
        val model = gson.fromJson(json, Quotes::class.java)

        Assert.assertEquals(etalon.base, model.base)
        Assert.assertEquals(etalon.date, model.date)
        Assert.assertEquals(etalon.rates, model.rates)
    }
}
