package com.grandemc.fazendas.manager

import java.util.UUID

class GoldBank(private val playerManager: PlayerManager) {
    fun gold(playerId: UUID): Double {
        return playerManager.player(playerId).gold()
    }

    fun has(playerId: UUID, amount: Double): Boolean {
        return gold(playerId) >= amount
    }

    fun deposit(playerId: UUID, amount: Double) {
        playerManager.player(playerId).depositGold(amount)
    }

    fun withdraw(playerId: UUID, amount: Double) {
        playerManager.player(playerId).withdrawGold(amount)
    }
}