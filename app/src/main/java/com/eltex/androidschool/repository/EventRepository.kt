package com.eltex.androidschool.repository

import com.eltex.androidschool.model.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getPost(): Flow<List<Event>>
    fun likeById(id: Long)
    fun participatedById(id: Long)
    fun addPost(id: Long, content: String)

    fun deleteById(id: Long)

    fun editById(id: Long?, content: String?)
}