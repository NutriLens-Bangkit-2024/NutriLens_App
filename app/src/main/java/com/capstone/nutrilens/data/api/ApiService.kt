package com.capstone.nutrilens.data.api

import com.github.mikephil.charting.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {
    private const val BASE_URL = "https://backend-services-n62gwjf46q-et.a.run.app/"

    private val client: Retrofit
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

    val instanceRetrofit: ApiConfig
        get() = client.create(ApiConfig::class.java)

    val scanningRetrofit: ApiConfig
        get() = scanningClient.create(ApiConfig::class.java)

    private val scanningClient: Retrofit
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://nutrion-grade-scanning-n62gwjf46q-et.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

    private val modelClient: Retrofit
        get() {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://nutrition-grade-model-n62gwjf46q-et.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
        }

    val modelRetrofit: ApiConfig
        get() = modelClient.create(ApiConfig::class.java)

}
