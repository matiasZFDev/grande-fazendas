package com.grandemc.fazendas.global

import org.bukkit.Location
import org.bukkit.World

fun World.getLocation(x: Int, y: Int, z: Int): Location {
    return getBlockAt(x, y, z).location
}

fun Location.min(other: Location): Location {
    return world.getLocation(
        kotlin.math.min(blockX, other.blockX),
        kotlin.math.min(blockY, other.blockY),
        kotlin.math.min(blockZ, other.blockZ)
    )
}

fun Location.max(other: Location): Location {
    return world.getLocation(
        kotlin.math.max(blockX, other.blockX),
        kotlin.math.max(blockY, other.blockY),
        kotlin.math.max(blockZ, other.blockZ)
    )
}