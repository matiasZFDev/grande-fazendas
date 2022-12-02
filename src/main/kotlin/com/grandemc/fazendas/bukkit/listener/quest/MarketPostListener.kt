package com.grandemc.fazendas.bukkit.listener.quest

import com.grandemc.fazendas.bukkit.event.MarketBuyEvent
import com.grandemc.fazendas.bukkit.event.MarketPostEvent
import com.grandemc.fazendas.config.model.quest.type.MarketPostQuest
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.storage.player.model.QuestType
import org.bukkit.event.EventHandler

class MarketPostListener(
    questManager: QuestManager
) : TypeMatchQuestListener<MarketPostEvent, MarketPostQuest>(
    questManager, QuestType.MARKET_POST
) {
    @EventHandler
    fun onPost(event: MarketPostEvent) = onProgressCheck(event)
}