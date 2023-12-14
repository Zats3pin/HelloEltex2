package com.eltex.androidschool.dao

import com.eltex.androidschool.model.Event

interface PostDao {
    fun getAll(): List<Event>
    fun save(event : Event): Event
    fun likeById(eventId: Long): Event
    fun deleteById(eventId: Long)
    fun ParticipatedById(eventId: Long): Event
    fun editById(id: Long?, content: String?): Event
}