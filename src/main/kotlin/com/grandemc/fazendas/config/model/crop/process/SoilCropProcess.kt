package com.grandemc.fazendas.config.model.crop.process

import com.grandemc.fazendas.config.model.block.PlaceBlocks
import com.grandemc.fazendas.global.VectorArea
import com.sk89q.worldedit.Vector
import org.bukkit.Location

abstract class SoilCropProcess(
    private val cropArea: VectorArea,
    private val soilBlocks: PlaceBlocks
) : CropProcess {
    override fun start(origin: Location, blocks: PlaceBlocks) {
        val soilVectorArea = VectorArea(
            cropArea.vectors().mapTo(mutableListOf()) { Vector(it.x, it.y - 1, it.z) }
        )
        CropProcess.setBlocks(origin, soilVectorArea, soilBlocks)
        CropProcess.setBlocks(origin, cropArea, blocks)
    }

    override fun grow(origin: Location, blocks: PlaceBlocks) {
        CropProcess.setBlocks(origin, cropArea, blocks)
    }
}