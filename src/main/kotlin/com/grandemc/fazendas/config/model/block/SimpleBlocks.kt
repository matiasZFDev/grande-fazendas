package com.grandemc.fazendas.config.model.block

import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.regions.CuboidRegion

class SimpleBlocks(private val block: BaseBlock) : PlaceBlocks {
    override fun placeAll(
        session: EditSession, from: Vector, to: Vector, changeVectors: Iterable<Vector>
    ) {
        val region = CuboidRegion(from, to)
        session.setBlocks(region, block)
        session.flushQueue()
    }
}