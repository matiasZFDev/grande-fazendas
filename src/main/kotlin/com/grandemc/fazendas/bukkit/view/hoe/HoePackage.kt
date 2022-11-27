package com.grandemc.fazendas.bukkit.view.hoe

import com.grandemc.fazendas.config.FarmHoeConfig
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class HoePackage(
    playerManager: PlayerManager,
    farmHoeConfig: FarmHoeConfig,
    goldBank: GoldBank
) : StatelessPackage(
    HoeMenuContainer::class,
    HoeProcessor(playerManager, farmHoeConfig),
    HoeClickHandler(playerManager, goldBank, farmHoeConfig)
)