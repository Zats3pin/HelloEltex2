package com.eltex.androidschool.repository

import android.content.Context
import androidx.core.content.edit
import com.eltex.androidschool.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SharedPreferencesEventRepository(
    private val context: Context
) : EventRepository {

    private companion object {
        const val EVENT_KEY = "EVENT_KEY"
        const val ID_KEY = "ID_KEY"
    }

    private val preferences = context.getSharedPreferences("events", Context.MODE_PRIVATE)
    private val state = MutableStateFlow(readEvents())
    private var nextId = readId()



    private fun readId(): Long = preferences.getLong(ID_KEY,0L)


    private fun readEvents() : List<Event>{
        val serializedEvents = preferences.getString(EVENT_KEY,null)

        return if (serializedEvents != null) {
            Json.decodeFromString(serializedEvents)
        }else{
            emptyList()
        }

    }

    override fun getPost(): Flow<List<Event>> = state.asStateFlow()

    override fun likeById(id: Long) {
        state.update { events ->
            events.map {
                if (id == it.id) {
                    it.copy(likedByMe = !it.likedByMe)
                } else {
                    it
                }
            }
        }
        sync()
    }

    override fun participatedById(id: Long) {
        state.update { events ->
            events.map {
                if (id == it.id) {
                    it.copy(participatedByMe = !it.participatedByMe)
                } else {
                    it
                }
            }
        }
        sync()
    }

    override fun addPost(content: String) {
        state.update { events ->
            buildList(events.size + 1) {
                add(
                    Event(
                        id = ++nextId,
                        content = content,
                        author = "me",
                        published = "Now",
                        likedByMe = false,
                        link = "https://m2.material.io/components/cards",
                        status = "offline",
                        timeStatus = "16.05.22 12:00",
                        participatedByMe = false
                    )
                )
                addAll(events)
            }
        }
        sync()
    }

    override fun deleteById(id: Long) {
        state.update { events ->
            events.filter {
                it.id != id
            }
        }
        sync()
    }

    override fun editById(id: Long?, content: String?) {
        state.update { events ->
            events.map { event ->
                if (event.id == id) {
                    event.copy(content = content.toString())
                } else {
                    event
                }
            }
        }
        sync()

    }
    private fun sync(){
        preferences.edit {
            putLong(ID_KEY, nextId)
            putString(EVENT_KEY, Json.encodeToString(state.value))
        }
    }


}