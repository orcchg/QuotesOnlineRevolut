package com.orcchg.quotes.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.orcchg.quotes.data.BASE_URL
import com.orcchg.quotes.data.Cloud
import com.orcchg.quotes.data.RestAdapter
import com.orcchg.quotes.domain.Quotes
import com.orcchg.quotes.domain.QuotesDeserializer
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class CloudModule {

    @Provides @Singleton
    fun provideDeserializer(): JsonDeserializer<Quotes> = QuotesDeserializer()

    @Provides @Singleton
    fun provideGson(deserializer: JsonDeserializer<Quotes>): Gson =
        GsonBuilder()
            .registerTypeAdapter(Quotes::class.java, deserializer)
            .setDateFormat("yyyy-MM-dd")
            .create()

    @Provides @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return logInterceptor
    }

    @Provides @Singleton
    fun provideOkHttpClient(logInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .readTimeout(16, TimeUnit.SECONDS)
            .connectTimeout(16, TimeUnit.SECONDS)
            .build()

    @Provides @Singleton
    fun provideRetrofitBuilder(gson: Gson, okHttpClient: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)

    @Provides @Singleton
    fun provideRestAdapter(retrofit: Retrofit.Builder): RestAdapter =
            retrofit.baseUrl(BASE_URL).build().create(RestAdapter::class.java)

    @Provides @Singleton
    fun provideCloud(restAdapter: RestAdapter): Cloud = Cloud(restAdapter)
}
