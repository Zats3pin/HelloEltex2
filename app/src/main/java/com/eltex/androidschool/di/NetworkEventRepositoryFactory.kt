package com.eltex.androidschool.di

import android.content.ContentResolver
import com.eltex.androidschool.api.EventsApi
import com.eltex.androidschool.api.MediaApi
import com.eltex.androidschool.repository.EventRepository
import com.eltex.androidschool.repository.NetworkEventRepository

class NetworkEventRepositoryFactory(
    private val eventApi: EventsApi,
    private val mediaApi: MediaApi,
    private val contentResolver: ContentResolver
) : EventRepositoryFactory {
    override fun create(): EventRepository =
        NetworkEventRepository(
            eventApi,
            mediaApi,
            contentResolver
        )
}
