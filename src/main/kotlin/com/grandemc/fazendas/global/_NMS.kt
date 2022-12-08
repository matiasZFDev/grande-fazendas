package com.grandemc.fazendas.global

import net.minecraft.server.v1_8_R3.EntityArmorStand
import net.minecraft.server.v1_8_R3.Packet
import net.minecraft.server.v1_8_R3.PacketListener
import org.bukkit.World
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player

fun <T : PacketListener> Player.sendPacket(packet: Packet<T>) {
    if (this !is CraftPlayer)
        return
    handle.playerConnection.sendPacket(packet)
}

fun newHologramLine(world: World, customName: String = ""): EntityArmorStand {
    return EntityArmorStand((world as CraftWorld).handle).apply {
        this.customName = customName
        customNameVisible = true
        isInvisible = true
        setGravity(false)
    }
}

fun Player.prepareHologram(lines: List<String>): Hologram {
    val armorStands = lines.map { newHologramLine(world, it) }
    return Hologram(armorStands.toMutableList())
}