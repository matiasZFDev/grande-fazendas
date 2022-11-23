package com.grandemc.fazendas.manager

import com.grandemc.fazendas.storage.player.model.FarmLand
import org.bukkit.Bukkit
import java.util.UUID

class LandManager(private val farmManager: FarmManager) {
    fun hasLand(playerId: UUID, id: Byte): Boolean {
        return farmManager.farm(playerId).hasLand(id)
    }

    fun land(playerId: UUID, id: Byte): FarmLand {
        return farmManager.farm(playerId).land(id) ?: throw Error(
            "O jogador ${Bukkit.getOfflinePlayer(playerId)} não possui o plantio #$id."
        )
    }
}