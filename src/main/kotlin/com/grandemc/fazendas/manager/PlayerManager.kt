package com.grandemc.fazendas.manager

import com.grandemc.fazendas.storage.player.model.*
import com.grandemc.post.external.lib.database.base.DatabaseService
import java.util.UUID

class PlayerManager(private val playerService: DatabaseService<UUID, FarmPlayer>) {
    fun player(playerId: UUID): FarmPlayer {
        return playerService.get(playerId) ?: throw Error(
            "Não há nenhum jogador com a id $playerId"
        )
    }

    fun allPlayers(): Collection<FarmPlayer> {
        return playerService.getAll()
    }

    fun hasPlayer(playerId: UUID): Boolean {
        return playerService.get(playerId) != null
    }

    fun registerPlayer(playerId: UUID) {
        playerService.insert(FarmPlayer(
            playerId, null, ItemStorage(), FarmHoe(CustomEnchant.values().map {
                HoeEnchant(it, 0)
            }, 0.0),0.0
        ))
    }
}