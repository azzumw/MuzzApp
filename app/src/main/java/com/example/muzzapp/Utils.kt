package com.example.muzzapp

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(time: Long): Pair<String, String> {
    val format = SimpleDateFormat("EEEE HH:mm", Locale.UK)
    val formattedDate = format.format(time)
    return Pair(formattedDate.substringBefore(' '), formattedDate.substringAfter(' '))
}