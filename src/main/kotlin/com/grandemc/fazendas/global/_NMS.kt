package com.grandemc.fazendas.global

import net.citizensnpcs.api.CitizensAPI
import net.citizensnpcs.api.npc.MemoryNPCDataStore
import net.minecraft.server.v1_8_R3.EntityArmorStand
import net.minecraft.server.v1_8_R3.Packet
import net.minecraft.server.v1_8_R3.PacketListener
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player

fun Location.spawnNPC(skinName: String) {
    val registry = CitizensAPI.createAnonymousNPCRegistry(MemoryNPCDataStore())
    val npc = registry.createNPC(EntityType.PLAYER, skinName)
    npc.spawn(this)
}

fun <T : PacketListener> Player.sendPacket(packet: Packet<T>) {
    if (this !is CraftPlayer)
        return
    handle.playerConnection.sendPacket(packet)
}

fun Player.prepareHologram(lines: List<String>): Hologram {
    val armorStands = lines.map {
        EntityArmorStand((world as CraftWorld).handle)
    }
    return Hologram(armorStands)
}