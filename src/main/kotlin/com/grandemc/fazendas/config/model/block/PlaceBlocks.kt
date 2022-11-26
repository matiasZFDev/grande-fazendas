package com.grandemc.fazendas.config.model.block

import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.Vector

interface PlaceBlocks {
    fun placeAll(session: EditSession, from: Vector, to: Vector, changeVectors: Iterable<Vector>)
}