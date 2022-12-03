package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.findWorld
import com.grandemc.fazendas.global.getWeWorld
import com.grandemc.fazendas.global.toLocation
import com.grandemc.fazendas.storage.player.model.FarmLand
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import com.sk89q.worldedit.Vector
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Location
import java.util.UUID

class LandManager(
    private val farmManager: FarmManager,
    private val farmsConfig: FarmsConfig,
    private val buildManager: BuildManager,
    private val locationManager: IslandLocationManager,
    private val islandConfig: IslandConfig,
) {
    fun hasLand(playerId: UUID, id: Byte): Boolean {
        return farmManager.farm(playerId).hasLand(id)
    }

    fun land(playerId: UUID, id: Byte): FarmLand {
        return farmManager.farm(playerId).land(id) ?: throw Error(
            "O jogador ${Bukkit.getOfflinePlayer(playerId)} não possui o plantio #$id."
        )
    }

    private fun buildLand(playerId: UUID, id: Byte) {
        val farm = farmsConfig.get().getFarmById(id)
        val landLevel = land(playerId, id).level()
        val farmLevel = farm.config.levels.level(landLevel)
        val schematic = farm.getSchematicByName(farmLevel.schematic)
        val baseLocation = locationManager.baseLocation(playerId)
        val weWorld = getWeWorld(islandConfig.get().worldName)
        playerId.runIfOnline {
            val origin = locationManager.islandOrigin(player.uniqueId, false)
            val centerVector = origin.add(schematic.cropVectors.mid())
            val location = centerVector.toLocation(
                islandConfig.get().worldName.findWorld()
            )
            playEffect(location, Effect.EXPLOSION_HUGE, 0)
        }
        buildManager.pasteSchematic(schematic.schematic, baseLocation, weWorld)
    }

    fun upgradeLand(playerId: UUID, landId: Byte) {
        val playerFarm = farmManager.farm(playerId)
        if (!playerFarm.hasLand(landId))
            playerFarm.addLand(FarmLand(
                landId, null, 1, 0, 0, null, true
            ))
        else
            land(playerId, landId).levelUp()
        buildLand(playerId, landId)
    }

    fun landSchematic(playerId: UUID, farmId: Byte): FarmsConfig.FarmSchematic {
        val landLevel = land(playerId, farmId).level()
        val farmData = farmsConfig.get().getFarmById(farmId)
        return farmData.getSchematicByName(farmData.config.levels.level(landLevel).schematic)
    }

    fun landCrops(playerId: UUID, farmId: Byte): List<Vector> {
        val origin = locationManager.islandOrigin(playerId, false)
        val farmVectors = landSchematic(playerId, farmId).cropVectors.vectors()
        return farmVectors.map { it.add(origin) }
    }

    fun playerLands(playerId: UUID): List<FarmLand> {
        return farmManager.farm(playerId).lands()
    }

    fun isLandMaxed(land: FarmLand): Boolean {
        return farmsConfig.get().getFarmById(land.typeId()).config.levels.isMaxLevel(
            land.level()
        )
    }

    fun addXp(playerId: UUID, landId: Byte, xp: Int, farmXp: Boolean = true) {
        land(playerId, landId).addXp(xp)

        if (farmXp)
            farmManager.farm(playerId).addXp(xp)
    }

    fun landLevelConfig(playerId: UUID, landId: Byte): FarmsConfig.FarmLevel {
        val landLevel = land(playerId, landId).level()
        return farmsConfig.get().getFarmById(landId).config.levels.level(landLevel)
    }
}