package com.grandemc.fazendas.global

import net.minecraft.server.v1_8_R3.EntityArmorStand
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving
import org.bukkit.Location
import org.bukkit.entity.Player

class Hologram(private val entities: MutableList<EntityArmorStand>) {
    fun send(player: Player, origin: Location) {
        entities.reversed().forEachIndexed { index, it ->
            it.setLocation(origin.x, origin.y + (index * 0.3), origin.z, 0f, 0f)
            player.sendPacket(PacketPlayOutSpawnEntityLiving(it))
        }
    }

    fun update(player: Player, newLines: List<String>, origin: Location) {
        adjustEntities(player, newLines.size)

        newLines.zip(entities).forEach { (customName, entity) ->
            entity.customName = customName
        }

        send(player, origin)
    }

    private fun adjustEntities(player: Player, size: Int): Boolean {
        var newModCount = 0

        while (entities.size < size) {
            newModCount++
            entities.add(newHologramLine(player.world))
        }

        while (entities.size > size) {
            val entity = entities.removeLast()
            player.sendPacket(PacketPlayOutEntityDestroy(entity.id))
        }

        return newModCount > 0
    }

    fun remove(player: Player) {
        entities.forEach {
            player.sendPacket(PacketPlayOutEntityDestroy(it.id))
        }
    }
}