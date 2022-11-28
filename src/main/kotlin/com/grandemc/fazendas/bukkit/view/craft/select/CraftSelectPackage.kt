package com.grandemc.fazendas.bukkit.view.craft.select

import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class CraftSelectPackage(
    industryConfig: IndustryConfig,
    itemsConfig: ItemsChunk,
    materialsConfig: MaterialsConfig
) : StatelessPackage(
    CraftSelectMenuContainer::class,
    CraftSelectProcessor(industryConfig, itemsConfig, materialsConfig),
    CraftSelectClickHandler()
)