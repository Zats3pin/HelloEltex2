package com.eltex.androidschool.repository

import com.eltex.androidschool.model.Event
import java.time.Instant


interface EventRepository {
    suspend fun getLatest(count: Int): List<Event>
    suspend fun getBefore(id: Long, count: Int): List<Event>
    suspend fun participate(id: Long): Event
    suspend fun editById(id: Long, content: String): Event
    suspend fun likeById(id: Long): Event
    suspend fun unLikeById(id: Long): Event
    suspend fun unParticipate(id: Long): Event
    suspend fun saveEvent(id: Long, content: String, datetime: Instant): Event
    suspend fun deleteById(id: Long)

}