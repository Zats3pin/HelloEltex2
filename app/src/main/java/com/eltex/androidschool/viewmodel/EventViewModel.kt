package com.eltex.androidschool.viewmodel

import androidx.lifecycle.ViewModel
import com.eltex.androidschool.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.model.Status
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy


class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _state: MutableStateFlow<EventUiState> = MutableStateFlow(EventUiState())
    val state: StateFlow<EventUiState> = _state.asStateFlow()

    init {
        load()
    }




    fun load() {
        _state.update { it.copy(status = Status.Loading) }

        repository.getEvents().subscribeBy(onSuccess = { data ->
            _state.update {
                it.copy(events = data, status = Status.Idle)
            }
        }, onError = { throwable ->
            _state.update {
                it.copy(status = Status.Error(throwable))
            }
        }).addTo(disposable)
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
            repository.likeById(
                post.id
            ).subscribeBy(onSuccess = { data ->
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
            }, onError = { throwable ->
                _state.update {
                    it.copy(status = Status.Error(throwable))
                }
            }).addTo(disposable)
        } else {
            repository.unLikeById(
                post.id
            ).subscribeBy(onSuccess = { data ->
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
            }, onError = { throwable: Throwable ->
                _state.update {
                    it.copy(status = Status.Error(throwable))
                }
            }).addTo(disposable)
        }
    }

    fun participate(event: Event) {
        _state.update { it.copy(status = Status.Loading) }
        if (!event.participatedByMe) {
            repository.participate(
                event.id
            ).subscribeBy(onSuccess = { data ->
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
            }, onError = { throwable ->
                _state.update {
                    it.copy(status = Status.Error(throwable))
                }
            }).addTo(disposable)
        } else {
            repository.unParticipate(
                event.id
            ).subscribeBy(onSuccess = { data ->
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
            },

                onError = { throwable ->
                    _state.update {
                        it.copy(status = Status.Error(throwable))
                    }
                }).addTo(disposable)
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
        repository.deleteById(
            id
        ).subscribeBy(onComplete = {
            _state.update { state ->
                state.copy(
                    events = state.events.orEmpty().filter { it.id != id },
                    status = Status.Idle,
                )
            }
        },

            onError = { throwable ->
                _state.update {
                    it.copy(status = Status.Error(throwable))
                }
            }).addTo(disposable)
    }


    fun editById(id: Long?, content: String?) {
        // todo
    }

    override fun onCleared() {
        disposable.dispose()
    }

}