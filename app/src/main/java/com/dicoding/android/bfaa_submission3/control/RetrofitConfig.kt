package com.dicoding.android.bfaa_submission3.control

import com.dicoding.android.bfaa_submission3.BuildConfig.DEBUG
import com.dicoding.android.bfaa_submission3.util.NetworkInfo.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {
    private val loggingInterceptor = if (DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val apiInstance: ApiService = retrofit.create(ApiService::class.java)
}