package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.QuestsConfig
import java.util.UUID

class StatsManager(
    private val questsConfig: QuestsConfig
) {
    fun dailyQuests(playerId: UUID): Byte {
        return questsConfig.get().dailyQuestsLimit()
    }
}