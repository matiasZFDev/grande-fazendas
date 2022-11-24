package com.grandemc.fazendas.manager

import com.boydti.fawe.FaweAPI
import com.boydti.fawe.`object`.clipboard.DiskOptimizedClipboard
import com.grandemc.fazendas.global.member
import com.sk89q.worldedit.CuboidClipboard
import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.ClipboardFormats
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.regions.CuboidRegion
import com.sk89q.worldedit.session.ClipboardHolder
import com.sk89q.worldedit.world.World
import org.bukkit.Material

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

    private fun newSession(world: World): EditSession {
        return FaweAPI.getEditSessionBuilder(world).fastmode(true).limitUnlimited().build()
    }

    private fun cropVectors(clipboard: Clipboard, cropHolderId: Int): List<Vector> {
        return clipboard.region.toList().filter {
            clipboard.getBlock(it).type == cropHolderId
        }
    }
}