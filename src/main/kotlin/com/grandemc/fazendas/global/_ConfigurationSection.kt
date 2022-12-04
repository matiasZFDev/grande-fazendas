package com.grandemc.fazendas.global

import org.bukkit.configuration.ConfigurationSection

fun ConfigurationSection.getFloat(key: String): Float = getDouble(key).toFloat()