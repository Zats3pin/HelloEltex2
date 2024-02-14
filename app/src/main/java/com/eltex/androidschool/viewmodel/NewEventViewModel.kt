package com.eltex.androidschool.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.androidschool.model.FileModel
import com.eltex.androidschool.repository.EventRepository
import com.eltex.androidschool.utils.Status
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant


@HiltViewModel(assistedFactory = NewEventViewModelFactory::class)
class NewEventViewModel @AssistedInject constructor(
    private val repository: EventRepository,
    @Assisted("event") private val eventId: Long
) : ViewModel() {
    private val _state = MutableStateFlow(NewPostUiState())
    val state = _state.asStateFlow()
    suspend fun save(content: String) {

        viewModelScope.launch {
            try {
                val data = repository.saveEvent(
                    eventId, content, datetime = Instant.now(), _state.value.file
                )
                _state.update {
                    it.copy(result = data, status = Status.Idle)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }

    }

    fun consumeError() {
        _state.update { it.copy(status = Status.Idle) }
    }

    suspend fun edit(eventId: Long, content: String) {
        viewModelScope.launch {
            try {
                val data = repository.editById(eventId, content)
                _state.update { it.copy(result = data, status = Status.Idle) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    fun saveFile(fileModel: FileModel?) = _state.update {
        it.copy(file = fileModel)
    }


}