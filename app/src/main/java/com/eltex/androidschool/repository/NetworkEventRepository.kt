package com.eltex.androidschool.repository

import com.eltex.androidschool.api.EventsApi
import com.eltex.androidschool.model.Event
import kotlinx.coroutines.delay
import java.time.Instant


class NetworkEventRepository(
    private val api: EventsApi
) : EventRepository {


    override suspend fun saveEvent(id: Long, content: String, datetime: Instant): Event = api.save(
        Event(
            id = id,
            content = content,
            datetime = datetime,
        )
    )

    override suspend fun deleteById(id: Long) = api.deleteById(id)
    override suspend fun getLatest(count: Int): List<Event> = api.getLatest(count).also {
        delay(10_000)
    }

    override suspend fun getBefore(id: Long, count: Int): List<Event> =
        api.getBefore(id, count).also {
            delay(10_000)
        }


    override suspend fun participate(id: Long): Event = api.participate(id)


    override suspend fun editById(id: Long, content: String): Event = api.save(Event(id, content))

    override suspend fun likeById(id: Long): Event = api.like(id)

    override suspend fun unLikeById(id: Long): Event = api.unLike(id)

    override suspend fun unParticipate(id: Long): Event = api.unParticipate(id)


}