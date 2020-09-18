package com.seven.myapplication.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object APIConfig {
    private const val BASE_URL : String = "https://www.mocky.io/v2/";
    private var retrofit: Retrofit? = null
    private val urlMap = HashMap<Int, String>()

    fun getUrl(page: Int): String? {
        urlMap.put(1, "59b3f0b0100000e30b236b7e")
        urlMap.put(2, "59ac28a9100000ce0bf9c236")
        urlMap.put(3, "59ac293b100000d60bf9c239")

        return urlMap.get(page)
    }

    val instance: Retrofit?
        get() {
            if (retrofit == null) {
                val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(300, TimeUnit.SECONDS)
                    .connectTimeout(300, TimeUnit.SECONDS)
                    .build()
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}