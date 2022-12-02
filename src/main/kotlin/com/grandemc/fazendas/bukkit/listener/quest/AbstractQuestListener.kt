package com.grandemc.fazendas.bukkit.listener.quest

import com.grandemc.fazendas.bukkit.event.QuestCompleteEvent
import com.grandemc.fazendas.bukkit.event.SourceEvent
import com.grandemc.fazendas.config.model.quest.type.Quest
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.global.callEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

abstract class AbstractQuestListener<E : SourceEvent, Q : Quest>(
    private val questManager: QuestManager,
    private val questType: QuestType
) : Listener {
    abstract fun questMatches(event: E, questConfig: Q): Boolean
    open fun advancePoints(event: E, quest: FarmQuest, questConfig: Q): Int = 1

    fun onProgressCheck(event: E) {
        if (!questManager.isQuestProgress(event.playerId(), questType))
            return

        val quest = questManager.currentQuest(event.playerId())!!
        Suppress("UNCHECKED_CAST")
        val questConfig = questManager.questConfig(quest.id()) as Q

        if (!questMatches(event, questConfig))
            return

        quest.advance(advancePoints(event, quest, questConfig))

        if (!questConfig.isDone(quest))
            return

        quest.complete()
        callEvent(QuestCompleteEvent(event.playerId(), quest.id()))
    }
}