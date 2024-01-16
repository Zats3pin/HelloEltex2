package com.eltex.androidschool.repository

import com.eltex.androidschool.model.Event
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.time.Instant


interface EventRepository {
    fun participate(id: Long): Single<Event>
    fun menu()
    fun share()

    fun editById(id: Long, content: String): Single<Event>
    fun getEvents():Single<List<Event>>
    fun likeById(id: Long): Single<Event>
    fun unLikeById(id: Long): Single<Event>
    fun unParticipate(id: Long): Single<Event>

    fun saveEvent(id: Long, content: String, datetime: Instant): Single<Event>
    fun deleteById(id: Long): Completable

}