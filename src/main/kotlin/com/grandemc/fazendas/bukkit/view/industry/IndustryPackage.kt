package com.grandemc.fazendas.bukkit.view.industry

import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.manager.IndustryManager
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class IndustryPackage(
    industryManager: IndustryManager,
    industryConfig: IndustryConfig,
    materialsConfig: MaterialsConfig
) : StatelessPackage(
    IndustryMenuContainer::class,
    IndustryProcessor(industryManager, industryConfig, materialsConfig),
    IndustryClickHandler()
)