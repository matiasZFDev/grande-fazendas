package com.grandemc.fazendas.bukkit.view.master

import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class MasterPackage(goldBank: GoldBank) : StatelessPackage(
    MasterMenuContainer::class,
    MasterProcessor(goldBank),
    MasterClickHandler()
)