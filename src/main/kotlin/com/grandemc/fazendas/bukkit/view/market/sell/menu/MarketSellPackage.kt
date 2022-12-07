package com.grandemc.fazendas.bukkit.view.market.sell.menu

import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage
import org.bukkit.conversations.ConversationFactory

class MarketSellPackage(
    storageManager: StorageManager,
    conversationFactory: ConversationFactory,
    marketManager: MarketManager
) : StatefulPackage<MarketSellContext>(
    MarketSellMenuContainer::class,
    MarketSellProcessor(storageManager),
    MarketSellClickHandler(conversationFactory, marketManager, storageManager)
)