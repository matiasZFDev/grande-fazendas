package com.grandemc.fazendas.manager

import com.boydti.fawe.FaweAPI
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
            .to(location)
            .build()
        Operations.complete(operation)
        session.flushQueue()
    }

    fun clearSchematic(clipboard: Clipboard, origin: Vector) {
        val world = clipboard.region.world ?: throw Error(
            "Mundo nulo ao momento de criar schematic"
        )
        val from = origin.add(clipboard.minimumPoint)
        val to = origin.add(clipboard.maximumPoint)
        val region = CuboidRegion(from, to)
        newSession(world).setBlocks(region, BaseBlock(Material.AIR.id))
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