package com.grandemc.fazendas.bukkit.view.craft.start

import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.manager.IndustryManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.view.pack.impl.StatefulPackage

class CraftStartPackage(
    storageManager: StorageManager,
    industryConfig: IndustryConfig,
    industryManager: IndustryManager
) : StatefulPackage<CraftContext>(
    CraftStartMenuContainer::class,
    CraftStartProcessor(storageManager, industryConfig, industryManager),
    CraftStartClickHandler(industryConfig, storageManager, industryManager)
)