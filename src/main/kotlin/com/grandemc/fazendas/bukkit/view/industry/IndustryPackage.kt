package com.grandemc.fazendas.bukkit.view.industry

import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.manager.IndustryManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class IndustryPackage(
    industryManager: IndustryManager,
    industryConfig: IndustryConfig,
    materialsConfig: MaterialsConfig,
    storageManager: StorageManager
) : StatelessPackage(
    IndustryMenuContainer::class,
    IndustryProcessor(industryManager, industryConfig, materialsConfig),
    IndustryClickHandler(industryManager, industryConfig, storageManager)
)