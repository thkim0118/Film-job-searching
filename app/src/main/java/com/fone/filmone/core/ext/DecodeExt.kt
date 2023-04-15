package com.fone.filmone.core.ext

import java.net.URLDecoder
import java.nio.charset.StandardCharsets

fun String.toDecoding(): String = URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
