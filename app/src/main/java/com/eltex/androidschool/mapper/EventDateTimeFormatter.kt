package com.eltex.androidschool.mapper

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class EventDateTimeFormatter @Inject constructor(
    private val zoneId: ZoneId
) {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
    fun formatInstant(instant: Instant): String {
        return formatter.format(instant.atZone(zoneId))
    }
}