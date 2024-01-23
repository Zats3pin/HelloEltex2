package com.eltex.androidschool.viewmodel

import com.eltex.androidschool.MainCoroutineRule
import com.eltex.androidschool.mapper.EventUiModelMapper
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.model.EventUiModel
import com.eltex.androidschool.utils.Status
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class EventViewModelTest {
@get:Rule
val mainDispatcherRule = MainCoroutineRule()

/*



    @Test
    fun `deleteById error than error in state`() {
        val testError = RuntimeException("Test Error")
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun deleteById(id: Long) = throw testError
                override suspend fun getEvents(): List<Event> = emptyList()
            }
        )
        viewModel.deleteById(121)
        assertEquals(testError, (viewModel.state.value.status as Status.Error).reason)
    }

    @Test
    fun `successful deleteById`() {
        val testEvent1 = Event(111, "Test Event 1")
        val initialEvents = mutableListOf(testEvent1)
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun deleteById(id: Long) {
                    initialEvents.removeIf { it.id == id }
                }
                override suspend fun getEvents(): List<Event> = initialEvents
            }
        )
        viewModel.deleteById(111)
        val resultEvents: List<EventUiModel> = viewModel.state.value.events ?: emptyList()
        assertFalse(resultEvents.any { it.id == testEvent1.id })
        assertTrue(viewModel.state.value.status is Status.Idle)
    }

    @Test
    fun `load events successfully`() {
        val testEvents = listOf(Event(111, "Test Event 1"), Event(222, "Test Event 2"))
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = testEvents
            }
        )
        viewModel.load()
        val resultEvents = viewModel.state.value.events ?: emptyList()
        val mapper = EventUiModelMapper()
        assertEquals(testEvents.map { mapper.map(it) }, resultEvents)
        assertTrue(viewModel.state.value.status is Status.Idle)
    }
    @Test
    fun `load events fails  `() {
        val testError = RuntimeException("Test Error")
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = throw testError
            }
        )
        viewModel.load()
        assertTrue(viewModel.state.value.status is Status.Error)
        assertEquals(testError, (viewModel.state.value.status as Status.Error).reason)
    }
    @Test
    fun `like event successfully`() {
        val testEvent = Event(1, "Test Event", likedByMe = false)
        val updatedEvent = testEvent.copy(likedByMe = true)
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = mutableListOf(testEvent)
                override suspend fun likeById(id: Long): Event = updatedEvent
            }
        )
        val mapper = EventUiModelMapper()
        viewModel.like(mapper.map(testEvent))
        val resultEvent =
            viewModel.state.value.events?.find { it.id == testEvent.id } ?: EventUiModel()
        assertEquals(mapper.map(updatedEvent).likedByMe, resultEvent.likedByMe)
        assertTrue(viewModel.state.value.status is Status.Idle)
    }
    @Test
    fun `like event fails`() {
        val testEvent = Event(1, "Test Event", likedByMe = false)
        val testError = RuntimeException("Test Error")
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = mutableListOf(testEvent)
                override suspend fun likeById(id: Long): Event = throw testError
            }
        )
        val mapper = EventUiModelMapper()
        viewModel.like(mapper.map(testEvent))
        assertEquals(testError, (viewModel.state.value.status as Status.Error).reason)
    }
    @Test
    fun `unlike event successfully`() {
        val testEvent = Event(1, "Test Event", likedByMe = true)
        val updatedEvent = testEvent.copy(likedByMe = false)
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = mutableListOf(testEvent)
                override suspend fun unLikeById(id: Long): Event = updatedEvent
            }
        )
        val mapper = EventUiModelMapper()
        viewModel.like(mapper.map(testEvent))
        val resultEvent =
            viewModel.state.value.events?.find { it.id == testEvent.id } ?: EventUiModel()
        assertEquals(mapper.map(updatedEvent).likedByMe, resultEvent.likedByMe)
        assertTrue(viewModel.state.value.status is Status.Idle)
    }
    @Test
    fun `unlike event fails`() {
        val testEvent = Event(1, "Test Event", likedByMe = true)
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = mutableListOf(testEvent)
                override suspend fun likeById(id: Long): Event = error("Not implemented")
            }
        )
        val mapper = EventUiModelMapper()
        viewModel.like(mapper.map(testEvent))
        val error = (viewModel.state.value.status as Status.Error).reason
        assertTrue(error is IllegalStateException)
        assertEquals("Not implemented", error.message)
    }
    @Test
    fun `participate event successfully`() {
        val testEvent = Event(1, "Test Event", participatedByMe = false)
        val updatedEvent = testEvent.copy(participatedByMe = true)
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = mutableListOf(testEvent)
                override suspend fun participate(id: Long): Event = updatedEvent
            }
        )
        val mapper = EventUiModelMapper()
        viewModel.participate(mapper.map(testEvent))
        val resultEvent =
            viewModel.state.value.events?.find { it.id == testEvent.id } ?: EventUiModel()
        assertEquals(mapper.map(updatedEvent).participatedByMe, resultEvent.participatedByMe)
        assertTrue(viewModel.state.value.status is Status.Idle)
    }
    @Test
    fun `participate event fails`() {
        val testEvent = Event(1, "Test Event", participatedByMe = false)
        val testError = RuntimeException("Test Error")
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = mutableListOf(testEvent)
                override suspend fun participate(id: Long): Event = throw testError
            }
        )
        val mapper = EventUiModelMapper()
        viewModel.participate(mapper.map(testEvent))
        assertEquals(testError, (viewModel.state.value.status as Status.Error).reason)
    }
    @Test
    fun `unparticipate event successfully`() {
        val testEvent = Event(1, "Test Event", participatedByMe = true)
        val updatedEvent = testEvent.copy(participatedByMe = false)
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = mutableListOf(testEvent)
                override suspend fun unParticipate(id: Long): Event = updatedEvent
            }
        )
        val mapper = EventUiModelMapper()
        viewModel.participate(mapper.map(testEvent))
        val resultEvent =
            viewModel.state.value.events?.find { it.id == testEvent.id } ?: EventUiModel()
        assertEquals(mapper.map(updatedEvent).participatedByMe, resultEvent.participatedByMe)
        assertTrue(viewModel.state.value.status is Status.Idle)
    }
    @Test
    fun `unParticipate event fails`() {
        val testEvent = Event(1, "Test Event", participatedByMe = true)
        val testError = RuntimeException("Test Error")
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = mutableListOf(testEvent)
                override suspend fun unParticipate(id: Long): Event = throw testError
            }
        )
        val mapper = EventUiModelMapper()
        viewModel.participate(mapper.map(testEvent))
        assertEquals(testError, (viewModel.state.value.status as Status.Error).reason)
    }
    @Test
    fun `when like doesn't change`() {
        val testEvent1 = Event(1, "Test Event 1", likedByMe = false)
        val testEvent2 = Event(2, "Test Event 2", likedByMe = false)
        val updatedEvent = testEvent1.copy(likedByMe = true)
        val initialEvents = mutableListOf(testEvent1, testEvent2)
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = initialEvents
                override suspend fun likeById(id: Long): Event = updatedEvent
            }
        )
        val mapper = EventUiModelMapper()
        viewModel.like(mapper.map(testEvent1))
        val resultEvent1 = viewModel.state.value.events?.find { it.id == testEvent1.id }
        val resultEvent2 = viewModel.state.value.events?.find { it.id == testEvent2.id }
        assertEquals(true, resultEvent1?.likedByMe)
        assertEquals(false, resultEvent2?.likedByMe)
    }
    @Test
    fun `when unparticipate doesn't change`() {
        val testEvent1 = Event(1, "Test Event 1", participatedByMe = true)
        val testEvent2 = Event(2, "Test Event 2", participatedByMe = true)
        val updatedEvent = testEvent1.copy(participatedByMe = false)
        val initialEvents = listOf(testEvent1, testEvent2)
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = initialEvents
                override suspend fun unParticipate(id: Long): Event = updatedEvent
            }
        )
        val mapper = EventUiModelMapper()
        viewModel.participate(mapper.map(testEvent1))
        val resultEvent1 = viewModel.state.value.events?.find { it.id == testEvent1.id }
        val resultEvent2 = viewModel.state.value.events?.find { it.id == testEvent2.id }
        assertEquals(false, resultEvent1?.participatedByMe)
        assertEquals(true, resultEvent2?.participatedByMe)
    }
    @Test
    fun `when participate doesn't change`() {
        val testEvent1 = Event(1, "Test Event 1", participatedByMe = false)
        val testEvent2 = Event(2, "Test Event 2", participatedByMe = false)
        val updatedEvent = testEvent1.copy(participatedByMe = true)
        val initialEvents = listOf(testEvent1, testEvent2)
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = initialEvents
                override suspend fun participate(id: Long): Event = updatedEvent
            }
        )
        val mapper = EventUiModelMapper()
        viewModel.participate(mapper.map(testEvent1))
        val resultEvent1 = viewModel.state.value.events?.find { it.id == testEvent1.id }
        val resultEvent2 = viewModel.state.value.events?.find { it.id == testEvent2.id }
        assertEquals(true, resultEvent1?.participatedByMe)
        assertEquals(false, resultEvent2?.participatedByMe)
    }
    @Test
    fun `when unlike doesn't change`() {
        val testEvent1 = Event(1, "Test Event 1", likedByMe = true)
        val testEvent2 = Event(2, "Test Event 2", likedByMe = true)
        val updatedEvent = testEvent1.copy(likedByMe = false)
        val initialEvents = listOf(testEvent1, testEvent2)
        val viewModel = EventViewModel(
            repository = object: TestEventRepository {
                override suspend fun getEvents(): List<Event> = initialEvents
                override suspend fun unLikeById(id: Long): Event = updatedEvent
            }
        )
        val mapper = EventUiModelMapper()
        viewModel.like(mapper.map(testEvent1))
        val resultEvent1 = viewModel.state.value.events?.find { it.id == testEvent1.id }
        val resultEvent2 = viewModel.state.value.events?.find { it.id == testEvent2.id }
        assertEquals(false, resultEvent1?.likedByMe)
        assertEquals(true, resultEvent2?.likedByMe)
    }


*/


}
