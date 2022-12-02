package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.QuestsConfig
import com.grandemc.fazendas.config.model.quest.type.Quest
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.fazendas.storage.player.model.QuestMaster
import com.grandemc.fazendas.storage.player.model.QuestType
import java.util.UUID

class QuestManager(
    private val questsConfig: QuestsConfig,
    private val farmManager: FarmManager
) {
    fun currentQuest(playerId: UUID): FarmQuest? {
        return master(playerId).current()
    }

    fun isQuestProgress(playerId: UUID, type: QuestType): Boolean {
        return currentQuest(playerId)?.let {
            !it.isDone() && it.type() == type
        } ?: false
    }

    fun questConfig(id: Byte): Quest {
        return questsConfig.get().getQuest(id)
    }

    fun currentConfig(playerId: UUID) : Quest {
        return questConfig(currentQuest(playerId)!!.id())
    }

    fun advanceProgress(playerId: UUID, points: Int = 1) {
        currentQuest(playerId)?.advance(points)
    }

    fun questsDone(playerId: UUID): Short {
        return master(playerId).questsDone()
    }

    fun master(playerId: UUID): QuestMaster {
        return farmManager.farm(playerId).questMaster()
    }

    fun history(): QuestsConfig.QuestHistory {
        return questsConfig.get().history()
    }

    fun dailyQuestsDone(playerId: UUID): Byte {
        return master(playerId).dailyQuestsDone()
    }

    fun historyProgress(playerId: UUID): Byte {
        return master(playerId).questHistoryProgress()
    }

    fun isHistoryQuest(playerId: UUID, questId: Byte): Boolean {
        return history().quests().any { it.id == questId }
    }
}