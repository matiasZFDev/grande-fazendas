package com.grandemc.fazendas.util

import org.bukkit.Location

abstract class AbstractCuboid : Cuboid {
    override fun isInside(location: Location): Boolean {
        return location.world == min().world &&
                location.blockX in min().blockX..max().blockX &&
                location.blockY in min().blockY..max().blockY &&
                location.blockZ in min().blockZ..max().blockZ
    }
}