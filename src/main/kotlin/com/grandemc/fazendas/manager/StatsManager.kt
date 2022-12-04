package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.QuestsConfig
import java.util.UUID

class StatsManager(
    private val questsConfig: QuestsConfig,
    private val playerManager: PlayerManager
) {
    fun dailyQuests(playerId: UUID): Byte {
        return questsConfig.get().dailyQuestsLimit()
    }

    fun boostedXp(playerId: UUID, xp: Int): Int {
        return xp
            .let {
                playerManager.player(playerId).booster()?.let { booster ->
                    (it * booster.boost()).toInt()
                } ?: it
            }
    }
}