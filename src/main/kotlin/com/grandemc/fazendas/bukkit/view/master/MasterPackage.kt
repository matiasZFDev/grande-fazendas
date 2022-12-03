package com.grandemc.fazendas.bukkit.view.master

import com.grandemc.post.external.lib.view.pack.impl.StatelessPackage

class MasterPackage : StatelessPackage(
    MasterMenuContainer::class,
    MasterProcessor(),
    MasterClickHandler()
)