package com.grandemc.fazendas.bukkit.listener.quest

import com.grandemc.fazendas.bukkit.event.MarketSellEvent
import com.grandemc.fazendas.config.model.quest.type.MarketSellQuest
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.storage.player.model.QuestType
import org.bukkit.event.EventHandler

class MarketSellListener(
    questManager: QuestManager
) : TypeMatchQuestListener<MarketSellEvent, MarketSellQuest>(
    questManager, QuestType.MARKET_SELL
) {

    @EventHandler
    fun onSell(event: MarketSellEvent) = onProgressCheck(event)
}