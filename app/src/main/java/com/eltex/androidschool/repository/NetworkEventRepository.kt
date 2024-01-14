package com.eltex.androidschool.repository
import com.eltex.androidschool.api.EventsApi
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.utils.Callback
import retrofit2.Call
import retrofit2.Response
class NetworkEventRepository(
    private val api: EventsApi
) : EventRepository {


    override fun getEvents(callback: Callback<List<Event>>) {
        api.getAll().enqueue(
            object : retrofit2.Callback<List<Event>> {
                override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                    if (response.isSuccessful) {
                        val body = requireNotNull(response.body())
                        callback.onSuccess(body)
                    } else {
                        callback.onError(RuntimeException("Response code: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                    callback.onError(t)
                }

            }
        )
    }

    override fun likeById(id: Long, callback: Callback<Event>) {
        api.like(id).enqueue(
            object : retrofit2.Callback<Event> {
                override fun onResponse(call: Call<Event>, response: Response<Event>) {
                    if (response.isSuccessful) {
                        val body = requireNotNull(response.body())
                        callback.onSuccess(body)
                    } else {
                        callback.onError(RuntimeException("Response code: ${response.code()}"))
                    }
                }

                override fun onFailure(call: Call<Event>, t: Throwable) {
                    callback.onError(t)
                }

            }
        )
    }

    override fun unLikeById(id: Long, callback: Callback<Event>) {
        api.unLike(id).enqueue(
            object: retrofit2.Callback<Event> {
                override fun onResponse(call: Call<Event>, response: Response<Event>) {
                    if (response.isSuccessful) {
                        val body = requireNotNull(response.body())
                        callback.onSuccess(body)
                    } else {
                        callback.onError(RuntimeException("Response code: ${response.code()}"))
                    }
                }
                override fun onFailure(call: Call<Event>, t: Throwable) {
                    callback.onError(t)
                }
            }
        )
    }

    override fun unParticipate(id: Long, callback: Callback<Event>) {
        api.unParticipate(id).enqueue(
            object: retrofit2.Callback<Event> {
                override fun onResponse(call: Call<Event>, response: Response<Event>) {
                    if (response.isSuccessful) {
                        val body = requireNotNull(response.body())
                        callback.onSuccess(body)
                    } else {
                        callback.onError(RuntimeException("Response code: ${response.code()}"))
                    }
                }
                override fun onFailure(call: Call<Event>, t: Throwable) {
                    callback.onError(t)
                }
            }
        )
    }


    override fun saveEvent(
        id: Long,
        content: String,
        callback: Callback<Event>,
    ) {
        api.save(
            Event(
                id = id,
                content = content,
                datetime = "2023-12-27T19:19:49.940Z"
            )).enqueue(
                object: retrofit2.Callback<Event> {
                    override fun onResponse(call: Call<Event>, response: Response<Event>) {
                        if (response.isSuccessful) {
                            val body = requireNotNull(response.body())
                            callback.onSuccess(body)
                        } else {
                            callback.onError(RuntimeException("Response code: ${response.code()}"))
                        }
                    }
                    override fun onFailure(call: Call<Event>, t: Throwable) {
                        callback.onError(t)
                    }
                }
                )
    }

    override fun deleteById(id: Long, callback: Callback<Unit>) {
        api.deleteById(id).enqueue(
            object: retrofit2.Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        val body = requireNotNull(response.body())
                        callback.onSuccess(body)
                    } else {
                        callback.onError(RuntimeException("Response code: ${response.code()}"))
                    }
                }
                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    callback.onError(t)
                }
            }
        )
    }

    override fun participate(id: Long, callback: Callback<Event>) {
        api.participate(id).enqueue(
            object: retrofit2.Callback<Event> {
                override fun onResponse(call: Call<Event>, response: Response<Event>) {
                    if (response.isSuccessful) {
                        val body = requireNotNull(response.body())
                        callback.onSuccess(body)
                    } else {
                        callback.onError(RuntimeException("Response code: ${response.code()}"))
                    }
                }
                override fun onFailure(call: Call<Event>, t: Throwable) {
                    callback.onError(t)
                }
            }
        )
    }


    override fun menu() {
        TODO("Not yet implemented")
    }

    override fun share() {
        TODO("Not yet implemented")
    }

    override fun editById(id: Long?, content: String?, callback: Callback<Event>) {
        TODO("Not yet implemented")
    }
}