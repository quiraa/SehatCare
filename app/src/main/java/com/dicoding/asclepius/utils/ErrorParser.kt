package com.dicoding.asclepius.utils

import java.net.UnknownHostException

fun errorParser(e: Exception): String {
	return when(e) {
		is UnknownHostException -> "Check your Internet"
		else -> e.message.toString()
	}
}