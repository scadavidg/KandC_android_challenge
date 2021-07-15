package com.kandc_android_challenge.data.services

import com.example.kandc_android_challenge.BuildConfig.API_URL
import com.google.gson.JsonArray
import com.kandc_android_challenge.utils.ROUTE_API_CONTACTS
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface APIService {

    @GET(ROUTE_API_CONTACTS)
    fun getContactsList(): Call<JsonArray>

    companion object {
        operator fun invoke(): APIService {
            val authInterceptor = Interceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("format", "json")
                    .build()

                val newRequest = chain.request()
                    .newBuilder()
                    .header("Content-Type", "application/json")
                    .url(url)
                    .build()

                chain.proceed(newRequest)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(APIService::class.java)
        }
    }
}
