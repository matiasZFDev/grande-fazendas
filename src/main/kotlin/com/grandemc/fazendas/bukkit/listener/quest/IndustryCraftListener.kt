package com.grandemc.fazendas.bukkit.listener.quest

import com.grandemc.fazendas.bukkit.event.IndustryCraftEvent
import com.grandemc.fazendas.bukkit.event.QuestCompleteEvent
import com.grandemc.fazendas.config.model.quest.type.CraftQuest
import com.grandemc.fazendas.global.asType
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.global.callEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class IndustryCraftListener(
    questManager: QuestManager
) : AbstractQuestListener<IndustryCraftEvent, CraftQuest>(questManager, QuestType.CRAFT) {
    @EventHandler
    fun onCraft(event: IndustryCraftEvent) = onProgressCheck(event)

    override fun questMatches(event: IndustryCraftEvent, questConfig: CraftQuest): Boolean {
        return event.materialId() == questConfig.recipeMaterialId()
    }
}