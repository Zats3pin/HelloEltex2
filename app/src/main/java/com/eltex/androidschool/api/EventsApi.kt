package com.eltex.androidschool.api

import com.eltex.androidschool.model.Event
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface EventsApi {
    @GET("/api/events/latest")
    suspend fun getLatest(@Query("count") count: Int): List<Event>

    @GET("/api/events/{id}/before")
    suspend fun getBefore(@Path("id") id: Long, @Query("count") count: Int): List<Event>

    @POST("/api/events")
    suspend fun save(@Body event: Event): Event

    @POST("/api/events/{id}/likes")
    suspend fun like(@Path("id") id: Long): Event

    @DELETE("/api/events/{id}/likes")
    suspend fun unLike(@Path("id") id: Long): Event

    @DELETE("/api/events/{id}")
    suspend fun deleteById(@Path("id") id: Long)

    @POST("/api/events/{id}/participants")
    suspend fun participate(@Path("id") id: Long): Event

    @DELETE("/api/events/{id}/participants")
    suspend fun unParticipate(@Path("id") id: Long): Event

}