package com.dicoding.asclepius.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(rawDate: String) : String? {
	val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
	val outputDateFormat = SimpleDateFormat("EEEE, d MMMM yyyy HH:mm:ss", Locale.getDefault())
	val date = inputDateFormat.parse(rawDate)
	return date?.let { outputDateFormat.format(it) }
}