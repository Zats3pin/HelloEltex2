package com.eltex.androidschool.viewmodel

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface NewEventViewModelFactory {
    fun create(@Assisted("event") eventId: Long): NewEventViewModel
}