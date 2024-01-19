package com.eltex.androidschool.viewmodel

import com.eltex.androidschool.model.Event
import com.eltex.androidschool.model.Status
import com.eltex.androidschool.repository.EventRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class EventViewModelTest {

    @Test
    fun `deleteById error then error in state`() {
        val testError = RuntimeException("TEST ERROR")

        val viewModel = EventViewModel(
            repository = object : EventRepository {
                override suspend fun participate(id: Long): Event = error("Not implemented")
                override suspend fun menu() {
                    TODO("Not yet implemented")
                }

                override suspend fun share() {
                    TODO("Not yet implemented")
                }

                override suspend fun editById(id: Long, content: String): Event = error("Not implemented")

                override suspend fun getEvents(): List<Event> = emptyList()

                override suspend fun likeById(id: Long): Event = error("Not implemented")

                override suspend fun unLikeById(id: Long): Event = error("Not implemented")

                override suspend fun unParticipate(id: Long): Event = error("Not implemented")

                override suspend fun saveEvent(
                    id: Long, content: String, datetime: Instant
                ): Event = error("Not implemented")

                override suspend fun deleteById(id: Long) = throw testError

            },
        )
        viewModel.deleteById(123)

        assertEquals(testError, (viewModel.state.value.status as Status.Error).reason)
    }
}
