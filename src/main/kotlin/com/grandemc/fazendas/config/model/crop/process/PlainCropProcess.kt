package com.grandemc.fazendas.config.model.crop.process

import com.grandemc.fazendas.config.model.block.PlaceBlocks
import com.grandemc.fazendas.global.*
import org.bukkit.Location

open class PlainCropProcess(private val cropsArea: VectorArea) : CropProcess {
    override fun start(origin: Location, blocks: PlaceBlocks) {
        CropProcess.setBlocks(origin, cropsArea, blocks)
    }

    override fun grow(origin: Location, blocks: PlaceBlocks) {
        CropProcess.setBlocks(origin, cropsArea, blocks)
    }
}