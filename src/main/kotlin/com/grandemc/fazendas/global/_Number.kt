package com.grandemc.fazendas.global

import com.grandemc.post.external.lib.global.dottedFormat

fun Short.dottedFormat(): String {
    return toInt().dottedFormat()
}