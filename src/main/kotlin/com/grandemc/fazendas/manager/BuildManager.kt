package com.grandemc.fazendas.manager

import com.grandemc.fazendas.global.newSession
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.session.ClipboardHolder
import com.sk89q.worldedit.world.World

class BuildManager {
    fun pasteSchematic(clipboard: Clipboard, location: Vector, world: World) {
        val session = newSession(world)
        val operation = ClipboardHolder(clipboard, world.worldData)
            .createPaste(session, world.worldData)
            .ignoreEntities(true)
            .to(location)
            .build()
        Operations.complete(operation)
        session.flushQueue()
    }
}