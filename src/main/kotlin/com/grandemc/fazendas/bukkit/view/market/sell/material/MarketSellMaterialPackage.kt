package com.grandemc.fazendas.bukkit.view.market.sell.material

import com.grandemc.fazendas.bukkit.view.market.sell.menu.MarketSellContext
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage

class MarketSellMaterialPackage(
    storageManager: StorageManager,
    itemsConfig: ItemsChunk
) : StatefulPackage<MarketSellContext>(
    MarketSellMaterialMenuContainer::class,
    MarketSellMaterialProcessor(storageManager, itemsConfig),
    MarketSellMaterialClickHandler()
)