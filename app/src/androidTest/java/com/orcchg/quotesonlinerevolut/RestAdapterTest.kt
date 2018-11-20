package com.orcchg.quotesonlinerevolut

import androidx.test.runner.AndroidJUnit4
import com.google.gson.GsonBuilder
import com.orcchg.quotes.data.BASE_URL
import com.orcchg.quotes.data.RestAdapter
import com.orcchg.quotes.domain.Quotes
import com.orcchg.quotes.domain.QuotesDeserializer
import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
class RestAdapterTest {

    @Test
    fun test() {
        val gson = GsonBuilder()
            .registerTypeAdapter(Quotes::class.java, QuotesDeserializer())
            .setDateFormat("yyyy-MM-dd")
            .create()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient())
            .baseUrl(BASE_URL)
            .build()

        val mockRetrofit = MockRetrofit.Builder(retrofit)
            .networkBehavior(NetworkBehavior.create())
            .build()

        val delegate = mockRetrofit.create(RestAdapter::class.java)
        val adapter = MockRestAdapter(delegate)

        adapter.quotes("EUR").subscribe({
            Assert.assertEquals(MockRestAdapter.etalon.base, it.base)
            Assert.assertEquals(MockRestAdapter.etalon.date, it.date)
            Assert.assertEquals(MockRestAdapter.etalon.rates, it.rates)
        }, Timber::e)
    }
}
