package com.eltex.androidschool.repository

import com.eltex.androidschool.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryEventRepository : EventRepository {
    private val state = MutableStateFlow(
       List(100) {
           Event(
               id = (it+1).toLong(),
               content = "$it Приглашаю провести уютный вечер за увлекательными играми! У нас есть несколько вариантов настолок, подходящих для любой компании.",
               author = "Lydia Westervelt",
               published = "11.05.22 11:21",
               likedByMe = false,
               link = "https://m2.material.io/components/cards",
               status = false,
               timeStatus = "16.05.22 12:00",
               participatedByMe = false,
           )
       }
           .reversed()
    )

    override fun getPost(): Flow<List<Event>> = state.asStateFlow()

    override fun likeById(id: Long) {
        state.update {events ->
            events.map {
                if (id == it.id) {
                    it.copy(likedByMe = !it.likedByMe)
                } else{
                    it
                }
            }
        }
    }

    override fun participateById(id: Long) {
        state.update {events ->
            events.map {
                if (id == it.id) {
                    it.copy(participatedByMe = !it.participatedByMe)
                } else{
                    it
                }
            }
        }
    }

}