package com.eltex.androidschool.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.androidschool.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val _state: MutableStateFlow<EventUiState> = MutableStateFlow(EventUiState())
    val state: StateFlow<EventUiState> = _state.asStateFlow()

    init {
        repository.getPost()
            .onEach { post ->
                _state.update {
                    it.copy(events = post)
                }
            }
            .launchIn(viewModelScope)
    }

    fun likeById(id:Long) {
        repository.likeById(id)
    }

    fun participateById(id:Long) {
        repository.participateById(id)
    }
}