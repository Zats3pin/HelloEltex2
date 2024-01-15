package com.eltex.androidschool.api

import com.eltex.androidschool.model.Event
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsApi {
    @GET("/api/events")
    fun getAll(): Single<List<Event>>

    @POST("/api/events")
    fun save(@Body event: Event): Single<Event>

    @POST("/api/events/{id}/likes")
    fun like(@Path("id") id: Long): Single<Event>

    @DELETE("/api/events/{id}/likes")
    fun unLike(@Path("id") id: Long): Single<Event>

    @DELETE("/api/events/{id}")
    fun deleteById(@Path("id") id: Long): Completable

    @POST("/api/events/{id}/participants")
    fun participate(@Path("id") id: Long): Single<Event>

    @DELETE("/api/events/{id}/participants")
    fun unParticipate(@Path("id") id: Long): Single<Event>


    companion object {
        val INSTANCE: EventsApi by lazy {
            RetrofitFactory.INSTANSE.create<EventsApi>()
        }
    }


}