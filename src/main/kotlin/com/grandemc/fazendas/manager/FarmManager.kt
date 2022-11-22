package com.grandemc.fazendas.manager

import com.grandemc.fazendas.storage.player.model.PrivateFarm
import java.util.UUID

class FarmManager(private val playerManager: PlayerManager) {
    fun farm(playerId: UUID): PrivateFarm {
        return playerManager.player(playerId).farm() ?: throw Error(
            "O jogador $playerId não possui fazenda."
        )
    }
}