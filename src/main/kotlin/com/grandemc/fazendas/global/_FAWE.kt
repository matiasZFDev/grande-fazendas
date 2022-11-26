package com.grandemc.fazendas.global

import com.boydti.fawe.FaweAPI
import com.grandemc.fazendas.util.Checkable
import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.world.World

fun newSession(world: World): EditSession {
    return FaweAPI
        .getEditSessionBuilder(world)
        .fastmode(true)
        .limitUnlimited()
        .build()
}

class VectorArea(private val vectors: List<Vector>) {
    private val min: Vector = vectors.minBy { it.x + it.y + it.z }
    private val max: Vector = vectors.maxBy { it.x + it.y + it.z }
    private val mapped = MappedVectors(vectors)

    fun vectors(): List<Vector> = vectors
    fun min(): Vector = min
    fun max(): Vector = max
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