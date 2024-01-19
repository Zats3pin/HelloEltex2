package com.eltex.androidschool.repository

import com.eltex.androidschool.model.Event
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.time.Instant


interface EventRepository {
    suspend fun participate(id: Long): Event
    suspend fun menu()
    suspend fun share()

    suspend fun editById(id: Long, content: String): Event
    suspend fun getEvents(): List<Event>
    suspend fun likeById(id: Long): Event
    suspend fun unLikeById(id: Long): Event
    suspend fun unParticipate(id: Long): Event

    suspend fun saveEvent(id: Long, content: String, datetime: Instant): Event
    suspend fun deleteById(id: Long)

}