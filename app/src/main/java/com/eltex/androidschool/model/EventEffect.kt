package com.eltex.androidschool.model

sealed interface EventEffect {
    data class LoadNextPage(val id: Long, val count: Int) : EventEffect
    data class LoadInitialPage(val count: Int) : EventEffect
    data class Like(val post: EventUiModel) : EventEffect
    data class Participate(val post: EventUiModel) : EventEffect
    data class Delete(val post: EventUiModel) : EventEffect

}