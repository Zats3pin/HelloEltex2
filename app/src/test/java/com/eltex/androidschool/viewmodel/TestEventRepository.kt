package com.eltex.androidschool.viewmodel

import com.eltex.androidschool.model.Event
import com.eltex.androidschool.repository.EventRepository
import java.time.Instant

interface TestEventRepository : EventRepository {
 override suspend fun menu() = error("Not implemented")
 override suspend fun share() = error("Not implemented")
 override suspend fun saveEvent(id: Long, content: String, datetime: Instant): Event = error("Not implemented")
 override suspend fun deleteById(id: Long): Unit = error("Not implemented")
 override suspend fun editById(id: Long, content: String): Event = error("Not implemented")
 override suspend fun getEvents(): List<Event> = error("Not implemented")
 override suspend fun likeById(id: Long): Event = error("Not implemented")
 override suspend fun unLikeById(id: Long): Event = error("Not implemented")
 override suspend fun participate(id: Long): Event = error("Not implemented")
 override suspend fun unParticipate(id: Long): Event = error("Not implemented")

}