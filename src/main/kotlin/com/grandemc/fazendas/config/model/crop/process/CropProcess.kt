package com.grandemc.fazendas.config.model.crop.process

import com.grandemc.fazendas.config.model.block.PlaceBlocks
import com.grandemc.fazendas.global.VectorArea
import com.grandemc.fazendas.global.getWeWorld
import com.grandemc.fazendas.global.newSession
import com.grandemc.fazendas.global.toWeVector
import com.sk89q.worldedit.EditSession
import org.bukkit.Location

interface CropProcess {
    fun start(origin: Location, blocks: PlaceBlocks)
    fun grow(origin: Location, blocks: PlaceBlocks)

    companion object {
        fun setBlocks(
            session: EditSession, origin: Location, vectorArea: VectorArea,
            blocks: PlaceBlocks
        ) {
            val originVector = origin.toWeVector()
            val min = vectorArea.min().add(originVector)
            val max = vectorArea.max().add(originVector)
            val changeVectors = vectorArea.vectors().map { it.add(originVector) }
            session.setMask {
                vectorArea.mapped().contains(
                    it.subtract(origin.x, 0.0, origin.z)
                )
            }
            blocks.placeAll(session, min, max, changeVectors)
        }

        fun setBlocks(origin: Location, vectorArea: VectorArea, blocks: PlaceBlocks) {
            val weWorld = getWeWorld(origin.world.name)
            setBlocks(newSession(weWorld), origin, vectorArea, blocks)
        }
    }
}