package com.eltex.androidschool.repository

import android.content.Context
import androidx.core.content.edit
import com.eltex.androidschool.dao.PostDao
import com.eltex.androidschool.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SqliteEventRepository (private val dao: PostDao) : EventRepository {



   // private val preferences = context.getSharedPreferences("events", Context.MODE_PRIVATE)
    private val state = MutableStateFlow(readEvents())


    private fun readEvents(): List<Event> = dao.getAll()


    override fun getPost(): Flow<List<Event>> = state.asStateFlow()

    override fun likeById(id: Long) {
     dao.likeById(id)
        state.update{readEvents()}
    }

    override fun participatedById(id: Long) {
        dao.ParticipatedById(id)
        state.update{readEvents()}
    }

    override fun addPost(content: String) {
        dao.save(Event(
            content = content,
            author = "ME",
            published = "NOW",
            link = "https://github.com/Zats3pin/HelloEltex2",
            status = "offline",
            timeStatus = "11.12.1997"))
        state.update{readEvents()}
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
        state.update{readEvents()}
    }

    override fun editById(id: Long?, content: String?) {
        dao.editById(id,content)
        state.update{readEvents()}

    }

}