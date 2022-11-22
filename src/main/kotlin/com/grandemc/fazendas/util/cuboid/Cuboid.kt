package com.grandemc.fazendas.util.cuboid

import org.bukkit.Location

interface Cuboid {
    fun isInside(location: Location): Boolean
    fun min(): Location
    fun max(): Location
}