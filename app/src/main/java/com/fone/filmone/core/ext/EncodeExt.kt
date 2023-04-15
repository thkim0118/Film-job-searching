package com.fone.filmone.core.ext

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.toEncoding(): String = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
