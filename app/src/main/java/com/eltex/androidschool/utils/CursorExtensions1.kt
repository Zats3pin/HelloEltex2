package com.eltex.androidschool.utils

import android.database.Cursor

fun Cursor.getLongOrThrow(columnName: String): Long {
    val columnIndex = getColumnIndexOrThrow(columnName)
    return getLong(columnIndex)
}

fun Cursor.getStringOrThrow(columnName: String): String {
    val columnIndex = getColumnIndexOrThrow(columnName)
    return getString(columnIndex)
}

fun Cursor.getIntOrThrow(columnName: String): Int {
    val columnIndex = getColumnIndexOrThrow(columnName)
    return getInt(columnIndex)
} // на будущее оставил

fun Cursor.getBooleanOrThrow(columnName: String): Boolean {
    val columnIndex = getColumnIndexOrThrow(columnName)
    return getInt(columnIndex) != 0
}