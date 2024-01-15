package com.eltex.androidschool.repository

import com.eltex.androidschool.api.EventsApi
import com.eltex.androidschool.model.Event
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.time.Instant


class NetworkEventRepository(
    private val api: EventsApi
) : EventRepository {

    override fun getEvents(): Single<List<Event>> = api.getAll()

    override fun saveEvent(id: Long, content: String): Single<Event> = api.save(
        Event(
            id = id,
            content = content,
            published = ""
   ))

    override fun deleteById(id: Long): Completable = api.deleteById(id)


    override fun participate(id: Long): Single<Event> = api.participate(id)

    override fun menu() {
        TODO("Not yet implemented")
    }

    override fun share() {
        TODO("Not yet implemented")
    }

    override fun editById(id: Long, content: String): Single<Event> = api.save(Event(id, content))


    override fun likeById(id: Long): Single<Event> = api.like(id)

    override fun unLikeById(id: Long): Single<Event> = api.unLike(id)

    override fun unParticipate(id: Long): Single<Event> = api.unParticipate(id)



}