package com.eltex.androidschool.mapper.di

import com.eltex.androidschool.mapper.EventDateTimeFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.ZoneId

@InstallIn(SingletonComponent::class)
@Module
class EventDateTimeFormatterModule {
    @Provides
    fun provideZoneId(): ZoneId = ZoneId.systemDefault()

    @Provides
    fun provideEventDateTimeFormatter(zoneId: ZoneId): EventDateTimeFormatter {
        return EventDateTimeFormatter(zoneId)
    }
}