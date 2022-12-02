package com.grandemc.fazendas.bukkit.listener.quest

import com.grandemc.fazendas.bukkit.event.CropCollectEvent
import com.grandemc.fazendas.bukkit.event.QuestCompleteEvent
import com.grandemc.fazendas.config.model.quest.type.CollectQuest
import com.grandemc.fazendas.global.asType
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.storage.player.model.QuestType
import com.grandemc.post.external.lib.global.callEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class CropCollectListener(
    questManager: QuestManager
) : AbstractQuestListener<CropCollectEvent, CollectQuest>(
    questManager, QuestType.CROP_COLLECT
) {
    @EventHandler
    fun onCollect(event: CropCollectEvent) = onProgressCheck(event)

    override fun questMatches(event: CropCollectEvent, questConfig: CollectQuest): Boolean {
        return event.cropId() == questConfig.cropId()
    }
}