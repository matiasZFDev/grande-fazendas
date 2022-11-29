package com.grandemc.fazendas.bukkit.view.market.selling

import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class MarketSellingPackage(
    marketManager: MarketManager,
    storageManager: StorageManager,
    itemsConfig: ItemsChunk
) : StatelessPackage(
    MarketSellingMenuContainer::class,
    MarketSellingProcessor(marketManager, storageManager, itemsConfig),
    MarketSellingClickHandler(marketManager, storageManager)
)