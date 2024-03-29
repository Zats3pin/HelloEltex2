package com.eltex.androidschool.mapper

import com.eltex.androidschool.model.EventStatus
import com.eltex.androidschool.model.EventUiModel
import com.eltex.androidschool.model.EventUiState
import com.eltex.androidschool.model.PagingModel
import com.eltex.androidschool.reducer.EventReducer.Companion.PAGE_SIZE

class EventPagingModelMapper {
    fun map(eventUiState: EventUiState): List<PagingModel<EventUiModel>> {
        val events = eventUiState.events.map {
            PagingModel.Data(it)
        }
        return when (val status = eventUiState.status) {
            is EventStatus.NextPageError -> events + PagingModel.Error(status.reason)
            EventStatus.NextPageLoading -> events + List(PAGE_SIZE) { PagingModel.Progress }
            else -> events
        }
    }
}