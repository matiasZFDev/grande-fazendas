package com.grandemc.fazendas.util

import org.bukkit.Location

class FixedCuboid(
    private val startLocation: Location,
    private val endLocation: Location
) : AbstractCuboid() {
    override fun min(): Location = startLocation
    override fun max(): Location = endLocation
}