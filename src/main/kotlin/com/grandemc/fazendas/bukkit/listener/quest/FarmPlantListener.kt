package com.grandemc.fazendas.bukkit.listener.quest

import com.grandemc.fazendas.bukkit.event.FarmPlantEvent
import com.grandemc.fazendas.bukkit.event.QuestCompleteEvent
import com.grandemc.fazendas.config.model.quest.type.PlantQuest
import com.grandemc.fazendas.global.asType
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.global.callEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class FarmPlantListener(
    questManager: QuestManager
) : AbstractQuestListener<FarmPlantEvent, PlantQuest>(questManager, QuestType.PLANT) {
    @EventHandler
    fun onPlant(event: FarmPlantEvent) = onProgressCheck(event)

    override fun questMatches(event: FarmPlantEvent, questConfig: PlantQuest): Boolean {
        return questConfig.cropId() == null || event.cropId() == event.cropId()
    }
}