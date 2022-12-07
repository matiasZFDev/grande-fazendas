package com.grandemc.fazendas.bukkit.view.hoe

import com.grandemc.fazendas.config.FarmHoeConfig
import com.grandemc.fazendas.manager.FarmItemManager
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class HoePackage(
    playerManager: PlayerManager,
    farmHoeConfig: FarmHoeConfig,
    goldBank: GoldBank,
    farmItemManager: FarmItemManager
) : StatelessPackage(
    HoeMenuContainer::class,
    HoeProcessor(playerManager, farmHoeConfig),
    HoeClickHandler(playerManager, goldBank, farmItemManager, farmHoeConfig)
)