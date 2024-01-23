package com.eltex.androidschool.reducer

import com.eltex.androidschool.model.EventEffect
import com.eltex.androidschool.model.EventMessage
import com.eltex.androidschool.model.EventStatus
import com.eltex.androidschool.model.EventUiState
import com.eltex.androidschool.mvi.Reducer
import com.eltex.androidschool.mvi.ReducerResult
import com.eltex.androidschool.utils.Either

class EventReducer : Reducer<EventUiState, EventEffect, EventMessage> {
    override fun reducer(
        old: EventUiState,
        message: EventMessage
    ): ReducerResult<EventUiState, EventEffect> = when (message) {
        is EventMessage.Delete -> TODO()
        is EventMessage.DeleteError -> TODO()
        EventMessage.HandleError -> TODO()
        is EventMessage.InitialLoaded -> ReducerResult(
            when (val result = message.result) {
                is Either.Left -> if (old.events.isEmpty()) {
                    old.copy(status = EventStatus.EmptyError(result.value))
                } else {
                    old.copy(singleError = result.value)
                }

                is Either.Right ->
                    old.copy(events = result.value, status = EventStatus.Idle)
            }
        )

        is EventMessage.Like -> TODO()
        is EventMessage.LikeResult -> TODO()
        EventMessage.LoadNextPage -> TODO()
        is EventMessage.NextPageLoaded -> TODO()
        is EventMessage.Participate -> TODO()
        EventMessage.Refresh -> ReducerResult(
            old.copy(
                status = if (old.events.isEmpty()) {
                    EventStatus.InitialLoading
                } else {
                    EventStatus.Refreshing
                },
            ),
            EventEffect.LoadInitialPage(15)
        )
    }

}