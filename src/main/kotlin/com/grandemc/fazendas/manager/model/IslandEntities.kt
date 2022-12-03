package com.grandemc.fazendas.manager.model

import com.grandemc.fazendas.global.Hologram
import net.citizensnpcs.api.npc.NPC
import org.bukkit.entity.Player

class IslandEntities(
    private val npcHolograms: List<Hologram>,
    private val landHolograms: LandHolograms,
    private val npcs: List<NPC>
) {
    fun updateHologram(player: Player, landId: Byte) {
        landHolograms.update(player, landId)
    }

    fun sendHolograms(player: Player) {
        landHolograms.sendAll(player)
    }

    fun clearAll(player: Player) {
        npcHolograms.forEach { it.remove(player) }
        landHolograms.clear(player)
        npcs.forEach { it.destroy() }
    }
}