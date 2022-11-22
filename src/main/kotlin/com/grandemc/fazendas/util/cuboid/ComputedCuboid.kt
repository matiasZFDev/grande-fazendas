package com.grandemc.fazendas.util.cuboid

import com.grandemc.fazendas.global.max
import com.grandemc.fazendas.global.min
import org.bukkit.Location

class ComputedCuboid(loc1: Location, loc2: Location) : AbstractCuboid() {
    private val startLocation = loc1.min(loc2)
    private val endLocation = loc1.max(loc2)

    override fun min(): Location = startLocation
    override fun max(): Location = endLocation
}