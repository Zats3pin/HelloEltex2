package com.eltex.androidschool.viewmodel
import com.eltex.androidschool.model.Event
import com.eltex.androidschool.model.Status
data class NewPostUiState(val result: Event? = null, val status: Status = Status.Idle)