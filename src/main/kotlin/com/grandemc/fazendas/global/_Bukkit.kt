package com.grandemc.fazendas.global

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.world.World
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.SkullType
import org.bukkit.block.Block
import org.bukkit.block.Skull
import org.bukkit.craftbukkit.v1_8_R3.block.CraftSkull
import java.util.UUID

fun getWeWorld(worldName: String): World {
    return Bukkit.getWorld(worldName).let(::BukkitWorld)
}

fun Block.setBlockSkull(base64Texture: String) {
    if (type != Material.SKULL)
        return

    val skull = state as Skull
    skull.skullType = SkullType.PLAYER
    val skullTile = (skull as CraftSkull).tileEntity
    val profile = GameProfile(UUID.randomUUID(), null)
    profile.properties.put("textures", Property("textures", base64Texture))
    skullTile.gameProfile = profile
    state.update(true)
}