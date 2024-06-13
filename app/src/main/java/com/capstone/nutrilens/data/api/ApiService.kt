package com.capstone.nutrilens.data.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {
    private const val BASE_URL = "https://backend-services-n62gwjf46q-et.a.run.app/"

//    private val interceptor = Interceptor { chain ->
//        val request = chain.request().newBuilder()
//            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJ1cm46YXVkaWVuY2U6dGVzdCIsImlzcyI6InVybjppc3N1ZXI6dGVzdCIsInN1YiI6Ik84TmV0ZDVhbGRSaXRyTjQiLCJleHAiOjE3MTgyNDkwMzksImlhdCI6MTcxODE2MjYzOX0.Q9_cGRj_9qucZj5jM9bXb3x1w2oqrxS-rxPBBu1mGB8")
//            .build()
//        chain.proceed(request)
//    }

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
}
