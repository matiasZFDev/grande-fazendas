package com.grandemc.fazendas.global

import net.minecraft.server.v1_8_R3.EntityArmorStand
import net.minecraft.server.v1_8_R3.Packet
import net.minecraft.server.v1_8_R3.PacketListener
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player

fun <T : PacketListener> Player.sendPacket(packet: Packet<T>) {
    if (this !is CraftPlayer)
        return
    handle.playerConnection.sendPacket(packet)
}

fun Player.prepareHologram(lines: List<String>): Hologram {
    val armorStands = lines.map {
        val entity = EntityArmorStand((world as CraftWorld).handle)
        entity.customName = it
        entity.customNameVisible = true
        entity.isInvisible = true
        entity.setGravity(false)
        entity
    }
    return Hologram(armorStands)
}