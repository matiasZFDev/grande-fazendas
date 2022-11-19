package com.grandemc.fazendas.global

import net.minecraft.server.v1_8_R3.EntityArmorStand
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving
import org.bukkit.Location
import org.bukkit.entity.Player

class Hologram(private val entities: List<EntityArmorStand>) {
    fun send(player: Player, origin: Location) {
        entities.reversed().forEachIndexed { index, it ->
            it.setLocation(origin.x, origin.y + (index * 0.2), origin.z, 0f, 0f)
            player.sendPacket(PacketPlayOutSpawnEntityLiving(it))
        }
    }

    fun update(player: Player, newLines: List<String>) {
        newLines.zip(entities).forEach { (customName, entity) ->
            entity.customName = customName
        }
    }
}