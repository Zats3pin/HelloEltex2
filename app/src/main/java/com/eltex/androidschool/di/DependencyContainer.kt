package com.eltex.androidschool.di

import androidx.lifecycle.ViewModelProvider

interface DependencyContainer {
    fun getEventViewModelFactory(): ViewModelProvider.Factory
    fun getNewEventViewModelFactory(id: Long): ViewModelProvider.Factory
}