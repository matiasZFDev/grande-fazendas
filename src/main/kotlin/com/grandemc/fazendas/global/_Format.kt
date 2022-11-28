package com.grandemc.fazendas.global

import com.grandemc.post.external.lib.global.dottedFormat
import com.grandemc.post.external.lib.global.toFormat

fun Short.toFormat(): String = toDouble().toFormat()
fun Int.commaFormat(): String = dottedFormat().replace('.', ',')
fun Short.commaFormat(): String = toInt().commaFormat()