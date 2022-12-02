package com.grandemc.fazendas.bukkit.listener.quest

import com.grandemc.fazendas.bukkit.event.MarketBuyEvent
import com.grandemc.fazendas.config.model.quest.type.MarketBuyQuest
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.storage.player.model.QuestType
import org.bukkit.event.EventHandler

class MarketBuyListener(
    questManager: QuestManager
) : TypeMatchQuestListener<MarketBuyEvent, MarketBuyQuest>(
    questManager, QuestType.MARKET_BUY
) {
    @EventHandler
    fun onBuy(event: MarketBuyEvent) = onProgressCheck(event)
}