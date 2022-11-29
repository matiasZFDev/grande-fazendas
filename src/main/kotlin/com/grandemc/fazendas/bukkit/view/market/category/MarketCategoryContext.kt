package com.grandemc.fazendas.bukkit.view.market.category

import com.grandemc.fazendas.manager.model.MarketFilter
import com.grandemc.post.external.lib.view.base.ContextData

class MarketCategoryContext(
    val categoryId: Byte,
    val page: Byte,
    val filter: MarketFilter
): ContextData