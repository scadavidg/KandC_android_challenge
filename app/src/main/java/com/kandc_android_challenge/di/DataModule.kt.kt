package com.kandc_android_challenge.di

import com.kandc_android_challenge.data.services.ContactService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import java.util.concurrent.TimeUnit.SECONDS

private const val TIME_OUT = 30L

val dataModule = module(override = true) {
    // Client
    single {
        OkHttpClient
            .Builder()
            .connectTimeout(TIME_OUT, SECONDS)
            .readTimeout(TIME_OUT, SECONDS)
            .build()
    }

    // services
    single { ContactService() }
}
