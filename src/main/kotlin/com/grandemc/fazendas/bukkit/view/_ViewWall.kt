package com.grandemc.fazendas.bukkit.view

import com.grandemc.fazendas.bukkit.view.land.LandContext
import com.grandemc.fazendas.provider.GlobalMenuContainerProvider
import com.grandemc.post.external.lib.view.base.ContextData
import com.grandemc.post.external.lib.view.pack.ViewPackage
import com.grandemc.post.external.lib.view.type.PackedView

class LandsView(viewPackage: ViewPackage<ContextData>) : PackedView<ContextData>(
    viewPackage, GlobalMenuContainerProvider.get()
)

class LandView(viewPackage: ViewPackage<LandContext>) : PackedView<LandContext>(
    viewPackage, GlobalMenuContainerProvider.get()
)