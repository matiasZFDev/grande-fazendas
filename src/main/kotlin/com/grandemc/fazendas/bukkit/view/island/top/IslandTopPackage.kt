package com.grandemc.fazendas.bukkit.view.island.top

import com.grandemc.fazendas.init.model.IslandTopState
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class IslandTopPackage(
    topState: IslandTopState,
    itemsConfig: ItemsChunk
) : StatelessPackage(
    IslandTopMenuContainer::class,
    IslandTopProcessor(topState, itemsConfig),
    IslandTopClickHandler()
)