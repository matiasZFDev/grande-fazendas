package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.getWeWorld
import com.grandemc.fazendas.global.findWorld
import com.grandemc.fazendas.global.min
import com.grandemc.fazendas.global.toLocation
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.Material
import java.util.UUID

class LandUpgradeManager(
    private val farmsConfig: FarmsConfig,
    private val buildManager: BuildManager,
    private val islandManager: IslandManager,
    private val islandConfig: IslandConfig
) {
    fun buildLand(playerId: UUID, id: Byte, level: Byte) {
        val farm = farmsConfig.get().getFarmById(id)
        val farmLevel = farm.config.levels.level(level)
        val schematic = farm.getSchematicByName(farmLevel.schematic)
        val baseLocation = islandManager.baseLocation(playerId)
        val weWorld = getWeWorld(islandConfig.get().worldName)
        val centerVector = baseLocation.add(schematic.schematic.region.min())
        playerId.runIfOnline {
            val location = Location(
                Bukkit.getWorld(islandConfig.get().worldName),
                centerVector.x + schematic.schematic.region.width / 2,
                baseLocation.y + schematic.schematic.region.height.toDouble() / 2,
                centerVector.z + schematic.schematic.region.length / 2
            )
            playEffect(location, Effect.EXPLOSION_HUGE, 0)
        }
        buildManager.pasteSchematic(schematic.schematic, baseLocation, weWorld)
    }
}