package com.grandemc.fazendas.manager.model

import com.grandemc.fazendas.global.Hologram
import net.citizensnpcs.api.npc.NPC
import org.bukkit.entity.Player

class IslandEntities(
    private val holograms: List<Hologram>,
    private val npcs: List<NPC>
) {
    fun clearAll(player: Player) {
        holograms.forEach { it.remove(player) }
        npcs.forEach { it.destroy() }
    }
}