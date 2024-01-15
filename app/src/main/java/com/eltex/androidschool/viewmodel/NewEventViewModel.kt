package com.eltex.androidschool.viewmodel

import androidx.lifecycle.ViewModel
import com.eltex.androidschool.model.Status
import com.eltex.androidschool.repository.EventRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
private val disposable = CompositeDisposable()
class NewEventViewModel(
    private val repository: EventRepository,
    private val eventId: Long
) : ViewModel() {
    private val _state = MutableStateFlow(NewPostUiState())
    val state = _state.asStateFlow()
    fun save(content: String) {
        repository.saveEvent(
            eventId, content
        ).subscribeBy(
            onSuccess = { data ->
                _state.update { it.copy(result = data, status = Status.Idle) }
            },

            onError = {throwable ->
                _state.update { it.copy(status = Status.Error(throwable)) }
            }
        ).addTo(disposable)
    }

    fun consumeError() {
        _state.update { it.copy(status = Status.Idle) }
    }

    fun edit(eventId: Long, content: String) {
        repository.editById(eventId, content).subscribeBy(
            onSuccess = { data ->
                _state.update { it.copy(result = data, status = Status.Idle) }
            },
            onError = {throwable ->
                _state.update { it.copy(status = Status.Error(throwable)) }
            }
        ).addTo(disposable)
    }

    override fun onCleared() {
        disposable.dispose()
    }

}