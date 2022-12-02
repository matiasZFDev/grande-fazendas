package com.grandemc.fazendas.bukkit.listener.quest

import com.grandemc.fazendas.bukkit.event.MaterialHandOverEvent
import com.grandemc.fazendas.config.model.quest.type.HandOverQuest
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.fazendas.storage.player.model.QuestType
import org.bukkit.event.EventHandler

class MaterialHandOverListener(
    questManager: QuestManager
) : AbstractQuestListener<MaterialHandOverEvent, HandOverQuest>(
    questManager, QuestType.HAND_OVER
) {
    @EventHandler
    fun onHandOver(event: MaterialHandOverEvent) = onProgressCheck(event)

    override fun questMatches(event: MaterialHandOverEvent, questConfig: HandOverQuest): Boolean {
        return event.materialId() == questConfig.materialId()
    }

    override fun advancePoints(
        event: MaterialHandOverEvent, quest: FarmQuest, questConfig: HandOverQuest
    ): Int {
        return event.amount().toInt()
    }
}