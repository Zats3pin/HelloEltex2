package com.eltex.androidschool.viewmodel

import androidx.lifecycle.ViewModel
import com.eltex.androidschool.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.model.Status
import com.eltex.androidschool.utils.Callback


class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val _state: MutableStateFlow<EventUiState> = MutableStateFlow(EventUiState())
    val state: StateFlow<EventUiState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        _state.update { it.copy(status = Status.Loading) }
        repository.getEvents(object : Callback<List<Event>> {
            override fun onSuccess(data: List<Event>) {
                _state.update {
                    it.copy(events = data, status = Status.Idle)
                }
            }

            override fun onError(throwable: Throwable) {
                _state.update {
                    it.copy(status = Status.Error(throwable))
                }
            }
        })
    }

    fun consumeError() {
        _state.update {
            if (it.status is Status.Error) {
                it.copy(status = Status.Idle)
            } else {
                it
            }
        }
    }

    fun like(post: Event) {
        _state.update { it.copy(status = Status.Loading) }
        if (!post.likedByMe) {
            repository.likeById(post.id, object : Callback<Event> {
                override fun onSuccess(data: Event) {
                    _state.update { state ->
                        state.copy(
                            events = state.events.orEmpty().map {
                                if (it.id == post.id) {
                                    data
                                } else {
                                    it
                                }
                            },
                            status = Status.Idle,
                        )
                    }
                }

                override fun onError(throwable: Throwable) {
                    _state.update {
                        it.copy(status = Status.Error(throwable))
                    }
                }
            })
        } else {
            repository.unLikeById(post.id, object : Callback<Event> {
                override fun onSuccess(data: Event) {
                    _state.update { state ->
                        state.copy(
                            events = state.events.orEmpty().map {
                                if (it.id == post.id) {
                                    data
                                } else {
                                    it
                                }
                            },
                            status = Status.Idle,
                        )
                    }
                }
                override fun onError(throwable: Throwable) {
                    _state.update {
                        it.copy(status = Status.Error(throwable))
                    }
                }
            })
        }
    }

    fun participate(event: Event) {
        _state.update { it.copy(status = Status.Loading) }
        if (!event.participatedByMe) {
            repository.participate(event.id, object : Callback<Event> {
                override fun onSuccess(data: Event) {
                    _state.update { state ->
                        state.copy(
                            events = state.events.orEmpty().map {
                                if (it.id == event.id) {
                                    data
                                } else {
                                    it
                                }
                            },
                            status = Status.Idle,
                        )
                    }
                }

                override fun onError(throwable: Throwable) {
                    _state.update {
                        it.copy(status = Status.Error(throwable))
                    }
                }
            })
        } else {
            repository.unParticipate(event.id, object : Callback<Event> {
                override fun onSuccess(data: Event) {
                    _state.update { state ->
                        state.copy(
                            events = state.events.orEmpty().map {
                                if (it.id == event.id) {
                                    data
                                } else {
                                    it
                                }
                            },
                            status = Status.Idle,
                        )
                    }
                }
                override fun onError(throwable: Throwable) {
                    _state.update {
                        it.copy(status = Status.Error(throwable))
                    }
                }
            })
        }
    }


    fun menu() {
        repository.menu()
    }

    fun share() {
        repository.share()
    }

    fun deleteById(id: Long) {
        _state.update { it.copy(status = Status.Loading) }
        repository.deleteById(id, object : Callback<Unit> {
            override fun onSuccess(data: Unit) {
                _state.update { state ->
                    state.copy(
                        events = state.events.orEmpty().filter { it.id != id },
                        status = Status.Idle,
                    )
                }
            }

            override fun onError(throwable: Throwable) {
                _state.update {
                    it.copy(status = Status.Error(throwable))
                }
            }
        })
    }

    fun editById(id: Long?, content: String?) {
        // todo
    }

}