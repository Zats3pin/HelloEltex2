package com.eltex.androidschool.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.androidschool.mapper.EventUiModelMapper
import com.eltex.androidschool.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.eltex.androidschool.model.EventUiModel
import com.eltex.androidschool.model.Status
import kotlinx.coroutines.launch


class EventViewModel(
    private val repository: EventRepository,
    private val mapper: EventUiModelMapper = EventUiModelMapper(),
) : ViewModel() {

    private val _state: MutableStateFlow<EventUiState> = MutableStateFlow(EventUiState())
    val state: StateFlow<EventUiState> = _state.asStateFlow()

    init {
        load()
    }


    fun load() {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {

            try {
                val events = repository.getEvents().map {
                    mapper.map(it)
                }


                _state.update {
                    it.copy(events = events, status = Status.Idle)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }


    fun like(event: EventUiModel) {
        _state.update { it.copy(status = Status.Loading) }
        viewModelScope.launch {
            try {
                val result = if (!event.likedByMe) {
                    repository.likeById(event.id)
                } else {
                    repository.unLikeById(event.id)
                }
                val uiModel = mapper.map(result)
                _state.update { state ->
                    state.copy(
                        events = state.events.orEmpty()
                            .map {
                                if (it.id == result.id) {
                                    uiModel
                                } else {
                                    it
                                }
                            },
                        status = Status.Idle,
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    fun participate(event: EventUiModel) {
        _state.update { it.copy(status = Status.Loading) }
        viewModelScope.launch {
            try {
                val result = if (!event.participatedByMe) {
                    repository.participate(event.id)
                } else {
                    repository.unParticipate(event.id)
                }
                val uiModel = mapper.map(result)
                _state.update { state ->
                    state.copy(
                        events = state.events.orEmpty().map {
                            if (it.id == event.id) {
                                uiModel
                            } else {
                                it
                            }
                        },
                        status = Status.Idle,
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }


    fun menu() {
        viewModelScope.launch {
            try {
                repository.menu()
            } catch (e: Exception) {
                //TODO
            }
        }
    }

    fun share() {
        viewModelScope.launch {
            try {
                repository.share()
            } catch (e: Exception) {
                //TODO
            }
        }
    }

    fun deleteById(id: Long) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {

            try {
                repository.deleteById(id)

                _state.update { state ->
                    state.copy(
                        events = state.events.orEmpty().filter { it.id != id },
                        status = Status.Idle,
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
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

}