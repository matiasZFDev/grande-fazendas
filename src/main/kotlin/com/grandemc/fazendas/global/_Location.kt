package com.grandemc.fazendas.global

import com.grandemc.fazendas.util.ViewVector
import com.sk89q.worldedit.Vector
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

fun Location.add(vector: ViewVector): Location {
    val copy = clone().add(vector.x(), vector.y(), vector.z())
    copy.yaw = copy.yaw + vector.yaw()
    copy.pitch = copy.pitch + vector.pitch()
    return copy
}

fun Location.add(vector: Vector): Location {
    return clone().add(vector.x, vector.y, vector.z)
}

fun Location.toWeVector(): Vector {
    return toVector().run { Vector(x, y, z) }
}