package com.grandemc.fazendas.config.model.block

import com.grandemc.fazendas.global.setBlockSkull
import com.grandemc.fazendas.global.toLocation
import com.grandemc.fazendas.util.Checkable
import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.Vector
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Skull
import org.bukkit.plugin.Plugin

class SkullBlocks(private val base64Texture: String): PlaceBlocks {
    override fun placeAll(
        session: EditSession, from: Vector, to: Vector, changeVectors: Iterable<Vector>
    ) {
        val world = Bukkit.getWorld(session.world.name)
        changeVectors.forEach {
            it.toLocation(world).block.run {
                type = Material.SKULL
                data = 0x1
                setBlockSkull(base64Texture)
            }
        }
    }
}