package com.eltex.androidschool.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eltex.androidschool.BuildConfig
import com.eltex.androidschool.api.EventsApi
import com.eltex.androidschool.api.MediaApi
import com.eltex.androidschool.effecthandler.EventEffectHandler
import com.eltex.androidschool.model.EventMessage
import com.eltex.androidschool.model.EventUiState
import com.eltex.androidschool.mvi.Store
import com.eltex.androidschool.reducer.EventReducer
import com.eltex.androidschool.viewmodel.EventViewModel
import com.eltex.androidschool.viewmodel.NewEventViewModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

class DependencyContainerImpl(private val context: Context) : DependencyContainer {
    companion object {
        private val JSON_TYPE = "application/json".toMediaType()
        private val JSON = Json {
            ignoreUnknownKeys = true
        }
    }

    private val okHttp by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor {
                it.proceed(
                    it.request()
                        .newBuilder()
                        .addHeader("Api-Key", BuildConfig.API_KEY)
                        .addHeader("Authorization", BuildConfig.AUTH_TOKEN)
                        .build()
                )
            }
            .let {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                } else {
                    it
                }
            }
            .build()
    }
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://eltex-android.ru/")
            .client(okHttp)
            .addConverterFactory(JSON.asConverterFactory(JSON_TYPE))
            .build()
    }
    private val eventApi: EventsApi by lazy {
        retrofit.create()
    }
    private val mediaApi: MediaApi by lazy {
        retrofit.create()
    }
    private val eventRepositoryFactory: EventRepositoryFactory = NetworkEventRepositoryFactory(
        eventApi, mediaApi, context.contentResolver,
    )

    override fun getEventViewModelFactory(): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                EventViewModel(
                    Store(
                        EventReducer(),
                        EventEffectHandler(
                            eventRepositoryFactory.create()
                        ),
                        setOf(EventMessage.Refresh),
                        EventUiState()
                    )
                )
            }
        }

    override fun getNewEventViewModelFactory(id: Long): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                NewEventViewModel(
                    repository = eventRepositoryFactory.create(),
                    eventId = id
                )
            }
        }
}