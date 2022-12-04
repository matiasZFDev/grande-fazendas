package com.grandemc.fazendas.global

import com.grandemc.post.external.lib.global.dottedFormat
import com.grandemc.post.external.lib.global.intFormat
import com.grandemc.post.external.lib.global.timeFormat
import com.grandemc.post.external.lib.global.toFormat

fun Short.toFormat(): String = toDouble().toFormat()
fun Int.commaFormat(): String = dottedFormat().replace('.', ',')
fun Short.commaFormat(): String = toInt().commaFormat()
fun Float.intFormat(): String = toDouble().intFormat()
fun Short.timeFormat(): String = toInt().timeFormat()