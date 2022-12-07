package com.grandemc.fazendas.global

import com.grandemc.fazendas.util.Range
import org.bukkit.configuration.ConfigurationSection

fun ConfigurationSection.getFloat(key: String): Float = getDouble(key).toFloat()
fun ConfigurationSection.getRange(key: String): Range {
    return getString(key).split("-").let {
        Range(it[0].toInt(), it[1].toInt())
    }
}