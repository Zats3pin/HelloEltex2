package com.eltex.androidschool.viewmodel

import androidx.lifecycle.ViewModel
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.model.Status
import com.eltex.androidschool.repository.EventRepository
import com.eltex.androidschool.utils.Callback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewEventViewModel(
    private val repository: EventRepository,
    private val eventId: Long
)     : ViewModel(){
    private val _state = MutableStateFlow(NewPostUiState())
    val state = _state.asStateFlow()
    fun save(content: String){
        repository.saveEvent(eventId, content, object: Callback<Event> {
            override fun onSuccess(data: Event) {
                _state.update { it.copy(result = data, status = Status.Idle) }
            }
            override fun onError(throwable: Throwable) {
                _state.update { it.copy(status = Status.Error(throwable)) }
            }
        })
    }
    fun consumeError(){
        _state.update { it.copy(status = Status.Idle) }
    }
    fun edit(eventId: Long, content: String) {
        repository.editById(eventId, content, object : Callback<Event> {
            override fun onSuccess(data: Event) {
                _state.update { it.copy(result = data, status = Status.Idle) }
            }

            override fun onError(throwable: Throwable) {
                _state.update { it.copy(status = Status.Error(throwable)) }
            }
        })
    }
}