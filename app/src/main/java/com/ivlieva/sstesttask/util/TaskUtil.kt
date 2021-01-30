package com.ivlieva.sstesttask.util

import android.text.Editable
import com.ivlieva.sstesttask.repository.local_data_source.realm.TaskCacheEntity
import io.realm.Realm
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime

fun setId(): Long {
    return try {
        (Realm.getDefaultInstance().where(TaskCacheEntity::class.java).max("id")?.toLong() ?: 0) + 1;
    } catch (ex: ArrayIndexOutOfBoundsException) {
        0
    }
}

fun formatDate(date: LocalDate): String {
    return String.format(
        "%s %s %s",
        date.dayOfMonth,
        date.month,
        date.year
    )
}

fun formatTime(time: LocalTime): String {
    return String.format(
        "%s:%s",
        time.hour,
        time.minute
    )
}

fun formatDateTime(dateTime: OffsetDateTime): String {
    return String.format(
        "%s, %s %s %s, %s:%s",
        dateTime.dayOfWeek,
        dateTime.dayOfMonth,
        dateTime.month,
        dateTime.year,
        dateTime.hour,
        dateTime.minute
    )
}

fun timeStrToMillis(str: String): Long {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result * 1000L * if (parts.size == 2) 60L else 1L
}