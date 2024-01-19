package com.eltex.androidschool.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType


object RetrofitFactory {
    private val JSON_TYPE = "application/json".toMediaType()
    private val JSON = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    val INSTANSE: Retrofit by lazy {
        Retrofit.Builder().baseUrl("https://eltex-android.ru/")
            .client(OkHttpFactory.INSTANCE)
            .addConverterFactory(JSON.asConverterFactory(JSON_TYPE))
            .build()
    }
}