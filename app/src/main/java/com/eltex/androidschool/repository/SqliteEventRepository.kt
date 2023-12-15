package com.eltex.androidschool.repository


import com.eltex.androidschool.dao.PostDao
import com.eltex.androidschool.entity.PostEntity
import com.eltex.androidschool.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update


class SqliteEventRepository(private val dao: PostDao) : EventRepository {

    override fun getPost(): Flow<List<Event>> = dao.getAll()
        .map {

            it.map(PostEntity::toEvent)
        }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun participatedById(id: Long) {
        dao.ParticipatedById(id)
    }

    override fun addPost(content: String) {
        dao.save(PostEntity.fromPost(
            Event(
                content = content,
                author = "ME",
                published = "NOW",
                link = "https://github.com/Zats3pin/HelloEltex2",
                status = "offline",
                timeStatus = "11.12.1997"
            )
        )
        )
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    override fun editById(id: Long?, content: String?) {
        dao.editById(id, content)
    }

}