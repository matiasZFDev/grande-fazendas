package com.grandemc.fazendas.global

import com.boydti.fawe.FaweAPI
import com.grandemc.fazendas.util.Checkable
import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.world.World
import org.bukkit.Location

fun newSession(world: World): EditSession {
    return FaweAPI
        .getEditSessionBuilder(world)
        .fastmode(true)
        .limitUnlimited()
        .build()
}

class VectorArea(private val vectors: List<Vector>) {
    private val min: Vector = vectors.run {
        Vector(
            minBy(Vector::getX).x,
            minBy(Vector::getY).y,
            minBy(Vector::getZ).z,
        )
    }
    private val max: Vector = vectors.run {
        Vector(
            maxBy(Vector::getX).x,
            maxBy(Vector::getY).y,
            maxBy(Vector::getZ).z,
        )
    }
    private val mapped = MappedVectors(vectors)

    fun vectors(): List<Vector> = vectors
    fun min(): Vector = min
    fun max(): Vector = max
    fun mid(): Vector {
        return Vector(
            min.x + ((max.x - min.x) / 2),
            min.y + ((max.y - min.y) / 2),
            min.z + ((max.z - min.z) / 2),
        )
    }
    fun mapped(): MappedVectors = mapped
}

class MappedVectors(private val vectors: Iterable<Vector>) : Checkable<Vector> {
    private val mapped: Map<Int, Map<Int, Set<Int>>>

    init {
        val mapped = mutableMapOf<Int, MutableMap<Int, MutableSet<Int>>>()
        vectors.forEach {
            mapped
                .computeIfAbsent(it.blockX) { mutableMapOf() }
                .computeIfAbsent(it.blockY) { mutableSetOf() }
                .add(it.blockZ)
        }
        this.mapped = mapped
    }

    override fun values(): Iterable<Vector> {
        return vectors
    }

    override fun contains(value: Vector): Boolean {
        return mapped[value.blockX]?.get(value.blockY)?.contains(value.blockZ) ?: false
    }
}

fun Vector.subtract(location: Location): Vector {
    return subtract(location.blockX, location.blockY, location.blockZ)
}