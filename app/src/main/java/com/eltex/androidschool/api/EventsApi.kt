package com.eltex.androidschool.api
import com.eltex.androidschool.model.Event
import retrofit2.Call
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsApi {
    @GET("/api/events")
    fun getAll(): Call<List<Event>>

    @POST("/api/events")
    fun save(@Body event: Event): Call<Event>

    @POST("/api/events/{id}/likes")
    fun like(@Path("id") id:Long): Call<Event>

    @DELETE("/api/events/{id}/likes")
    fun unLike(@Body event: Long): Call<Event>

    @DELETE("/api/events/{id}")
    fun deleteById(@Body event: Long): Call<Unit>

    companion object{
        val INSTANCE : EventsApi by lazy {
            RetrofitFactory.INSTANSE.create<EventsApi>()
        }
    }

    @POST("/api/events/{id}/participants")
    fun participate(@Path("id") id:Long): Call<Event>

    @DELETE("/api/events/{id}/participants")
    fun unParticipate(@Body event: Long): Call<Event>

}