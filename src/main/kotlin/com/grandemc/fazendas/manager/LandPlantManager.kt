package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.VectorArea
import com.grandemc.fazendas.global.findWorld
import com.grandemc.fazendas.global.toLocation
import com.grandemc.fazendas.storage.player.model.FarmLand
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import com.sk89q.worldedit.Vector
import org.bukkit.CropState
import org.bukkit.Effect
import org.bukkit.EntityEffect
import java.util.UUID

class LandPlantManager(
    private val islandConfig: IslandConfig,
    private val locationManager: IslandLocationManager,
    private val landManager: LandManager
) {
    fun startPlantation(playerId: UUID, landId: Byte, cropData: CropsConfig.Crop) {
        val playerLand = landManager.land(playerId, landId)
        plant(playerId, landId, cropData)
        playerLand.setCrop(cropData.id)
        playerLand.setCountdown(cropData.reset)
    }

    fun plant(playerId: UUID, landId: Byte, cropData: CropsConfig.Crop) {
        val world = islandConfig.get().worldName.findWorld()
        val origin = locationManager.islandOrigin(playerId, false)
        val cropsArea = landManager.landSchematic(playerId, landId).cropVectors
        val process = cropData.process.type.initializeProcess(cropsArea)
        process.start(origin.toLocation(world), cropData.process.startBlocks)
        playCropsEffect(playerId, cropsArea, origin, Effect.VILLAGER_THUNDERCLOUD)
    }

    fun growPlantation(playerId: UUID, land: FarmLand, cropData: CropsConfig.Crop) {
        val world = islandConfig.get().worldName.findWorld()
        val origin = locationManager.islandOrigin(playerId, false)
        val cropsArea = landManager.landSchematic(playerId, land.typeId()).cropVectors
        val process = cropData.process.type.initializeProcess(cropsArea)
        process.grow(origin.toLocation(world), cropData.process.grownBlocks)
        playCropsEffect(playerId, cropsArea, origin, Effect.HAPPY_VILLAGER)
    }

    private fun playCropsEffect(
        playerId: UUID, cropsArea: VectorArea, origin: Vector, effect: Effect
    ) {
        playerId.runIfOnline {
            cropsArea.vectors().shuffled().take(cropsArea.vectors().size / 3).forEach {
                playEffect(origin.add(it).toLocation(world), effect, 0)
            }
        }
    }
}