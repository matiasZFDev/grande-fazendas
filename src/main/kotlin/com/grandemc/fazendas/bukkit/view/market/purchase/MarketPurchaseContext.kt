package com.grandemc.fazendas.bukkit.view.market.purchase

import com.grandemc.fazendas.bukkit.view.market.category.MarketCategoryContext
import com.grandemc.fazendas.storage.market.model.MarketItem
import com.grandemc.post.external.lib.view.base.ContextData

class MarketPurchaseContext(
    val product: MarketItem,
    val categoryContext: MarketCategoryContext
): ContextData