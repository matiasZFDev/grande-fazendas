package com.grandemc.fazendas.bukkit.listener.quest

import com.grandemc.fazendas.bukkit.event.XpGainEvent
import com.grandemc.fazendas.config.model.quest.type.XpEarnQuest
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.fazendas.storage.player.model.QuestType
import org.bukkit.event.EventHandler

class XpGainListener(
    questManager: QuestManager
) : AbstractQuestListener<XpGainEvent, XpEarnQuest>(
    questManager, QuestType.XP_EARN
) {
    @EventHandler
    fun onGain(event: XpGainEvent) = onProgressCheck(event)

    override fun questMatches(event: XpGainEvent, questConfig: XpEarnQuest): Boolean {
        return true
    }

    override fun advancePoints(event: XpGainEvent, quest: FarmQuest, questConfig: XpEarnQuest): Int {
        return event.xp()
    }
}