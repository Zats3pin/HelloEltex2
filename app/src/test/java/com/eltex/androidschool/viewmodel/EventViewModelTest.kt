package com.eltex.androidschool.viewmodel

import com.eltex.androidschool.model.Event
import com.eltex.androidschool.model.Status
import com.eltex.androidschool.repository.EventRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class EventViewModelTest {

    @Test
    fun `deleteById error then error in state`() {
        val testError = RuntimeException("TEST ERROR")

        val viewModel = EventViewModel(
            repository = object : EventRepository {
                override fun participate(id: Long): Single<Event> = Single.never()
                override fun menu() {
                    TODO("Not yet implemented")
                }

                override fun share() {
                    TODO("Not yet implemented")
                }

                override fun editById(id: Long, content: String): Single<Event> = Single.never()

                override fun getEvents(): Single<List<Event>> = Single.just(emptyList())

                override fun likeById(id: Long): Single<Event> = Single.never()

                override fun unLikeById(id: Long): Single<Event> = Single.never()

                override fun unParticipate(id: Long): Single<Event> = Single.never()

                override fun saveEvent(
                    id: Long, content: String, datetime: Instant
                ): Single<Event> = Single.never()

                override fun deleteById(id: Long): Completable = Completable.error(testError)

            },
            schedulersFactory = TestSchedulersFactory,
        )
        viewModel.deleteById(123)

        assertEquals(testError, (viewModel.state.value.status as Status.Error).reason)
    }
}
