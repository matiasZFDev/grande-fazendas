package com.grandemc.fazendas.bukkit.view.market.sold

import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.fazendas.manager.controller.MarketSoldItemController
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class MarketSoldPackage(
    soldItemController: MarketSoldItemController,
    marketManager: MarketManager,
    storageManager: StorageManager,
    itemsConfig: ItemsChunk,
    goldBank: GoldBank
) : StatelessPackage(
    MarketSoldMenuContainer::class,
    MarketSoldProcessor(soldItemController, marketManager, storageManager, itemsConfig),
    MarketSoldClickHandler(soldItemController, marketManager, goldBank)
)