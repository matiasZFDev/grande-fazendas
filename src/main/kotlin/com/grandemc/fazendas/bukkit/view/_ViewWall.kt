package com.grandemc.fazendas.bukkit.view

import com.grandemc.fazendas.bukkit.view.craft.start.CraftContext
import com.grandemc.fazendas.bukkit.view.fertilizing.FertilizingContext
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

class LandPlantView(viewPackage: ViewPackage<LandContext>) : PackedView<LandContext>(
    viewPackage, GlobalMenuContainerProvider.get()
)

class FertilizingView(viewPackage: ViewPackage<FertilizingContext>) : PackedView<FertilizingContext>(
    viewPackage, GlobalMenuContainerProvider.get()
)

class HoeView(viewPackage: ViewPackage<ContextData>) : PackedView<ContextData>(
    viewPackage, GlobalMenuContainerProvider.get()
)

class StorageView(viewPackage: ViewPackage<ContextData>) : PackedView<ContextData>(
    viewPackage, GlobalMenuContainerProvider.get()
)

class IndustryView(viewPackage: ViewPackage<ContextData>) : PackedView<ContextData>(
    viewPackage, GlobalMenuContainerProvider.get()
)

class CraftSelectView(viewPackage: ViewPackage<ContextData>) : PackedView<ContextData>(
    viewPackage, GlobalMenuContainerProvider.get()
)

class CraftStartView(viewPackage: ViewPackage<CraftContext>) : PackedView<CraftContext>(
    viewPackage, GlobalMenuContainerProvider.get()
)