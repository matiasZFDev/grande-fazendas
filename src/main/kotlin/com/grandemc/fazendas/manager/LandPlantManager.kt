package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.findWorld
import com.grandemc.fazendas.global.toLocation
import com.grandemc.fazendas.storage.player.model.FarmLand
import java.util.UUID

class LandPlantManager(
    private val islandConfig: IslandConfig,
    private val locationManager: IslandLocationManager,
    private val landManager: LandManager
) {
    fun startPlantation(playerId: UUID, landId: Byte, cropData: CropsConfig.Crop) {
        val playerLand = landManager.land(playerId, landId)
        val world = islandConfig.get().worldName.findWorld()
        val origin = locationManager.islandOrigin(playerId, false).toLocation(world)
        val cropsArea = landManager.landSchematic(playerId, landId).cropVectors
        val process = cropData.process.type.initializeProcess(cropsArea)
        process.start(origin, cropData.process.startBlocks)
        playerLand.setCrop(cropData.id)
        playerLand.setCountdown(cropData.reset)
    }

    fun growPlantation(playerId: UUID, land: FarmLand, cropData: CropsConfig.Crop) {
        val world = islandConfig.get().worldName.findWorld()
        val origin = locationManager.islandOrigin(playerId, false).toLocation(world)
        val cropsArea = landManager.landSchematic(playerId, land.typeId()).cropVectors
        val process = cropData.process.type.initializeProcess(cropsArea)
        process.grow(origin, cropData.process.grownBlocks)
    }
}