package com.grandemc.fazendas.bukkit.listener.quest

import com.grandemc.fazendas.bukkit.event.SourceEvent
import com.grandemc.fazendas.config.model.quest.type.Quest
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.storage.player.model.QuestType

abstract class TypeMatchQuestListener<E : SourceEvent, Q : Quest>(
    questManager: QuestManager, questType: QuestType
) : AbstractQuestListener<E, Q>(questManager, questType) {
    override fun questMatches(event: E, questConfig: Q): Boolean {
        return true
    }
}