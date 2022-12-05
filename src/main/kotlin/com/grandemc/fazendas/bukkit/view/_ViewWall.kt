package com.grandemc.fazendas.bukkit.view

import com.grandemc.fazendas.bukkit.view.craft.start.CraftContext
import com.grandemc.fazendas.bukkit.view.fertilizing.FertilizingContext
import com.grandemc.fazendas.bukkit.view.land.LandContext
import com.grandemc.fazendas.bukkit.view.market.category.MarketCategoryContext
import com.grandemc.fazendas.bukkit.view.market.purchase.MarketPurchaseContext
import com.grandemc.fazendas.bukkit.view.market.sell.menu.MarketSellContext
import com.grandemc.fazendas.bukkit.view.quests.hand_over.HandOverContext
import com.grandemc.fazendas.bukkit.view.sell.MaterialSellContext
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

class MarketView(viewPackage: ViewPackage<PageContext>) : PackedView<PageContext>(
    viewPackage, GlobalMenuContainerProvider.get()
) {
    override fun getDefaultData(): PageContext {
        return PageContext(0)
    }
}

class MarketCategoryView(
    viewPackage: ViewPackage<MarketCategoryContext>
) : PackedView<MarketCategoryContext>(viewPackage, GlobalMenuContainerProvider.get())

class MarketSellView(
    viewPackage: ViewPackage<MarketSellContext>
) : PackedView<MarketSellContext>(viewPackage, GlobalMenuContainerProvider.get())

class MarketSellMaterialView(
    viewPackage: ViewPackage<MarketSellContext>
) : PackedView<MarketSellContext>(viewPackage, GlobalMenuContainerProvider.get())

class MarketSellingView(viewPackage: ViewPackage<ContextData>) : PackedView<ContextData>(
    viewPackage, GlobalMenuContainerProvider.get()
)

class MarketPurchaseView(
    viewPackage: ViewPackage<MarketPurchaseContext>
) : PackedView<MarketPurchaseContext>(viewPackage, GlobalMenuContainerProvider.get())

class QuestsView(viewPackage: ViewPackage<ContextData>) : PackedView<ContextData>(
    viewPackage, GlobalMenuContainerProvider.get()
)

class QuestHandOverView(
    viewPackage: ViewPackage<HandOverContext>
) : PackedView<HandOverContext>(viewPackage, GlobalMenuContainerProvider.get()) {
    override fun getDefaultData(): HandOverContext {
        return HandOverContext(1)
    }
}

class QuestHistoryView(viewPackage: ViewPackage<PageContext>) : PackedView<PageContext>(
    viewPackage, GlobalMenuContainerProvider.get()
) {
    override fun getDefaultData(): PageContext {
        return PageContext(0)
    }
}

class MaterialSellView(
    viewPackage: ViewPackage<MaterialSellContext>
) : PackedView<MaterialSellContext>(viewPackage, GlobalMenuContainerProvider.get())

class MasterView(viewPackage: ViewPackage<ContextData>) : PackedView<ContextData>(
    viewPackage, GlobalMenuContainerProvider.get()
)

class IslandView(viewPackage: ViewPackage<ContextData>) : PackedView<ContextData>(
    viewPackage, GlobalMenuContainerProvider.get()
)

class IslandTopView(viewPackage: ViewPackage<ContextData>) : PackedView<ContextData>(
    viewPackage, GlobalMenuContainerProvider.get()
)

class UpgradesView(viewPackage: ViewPackage<ContextData>) : PackedView<ContextData>(
    viewPackage, GlobalMenuContainerProvider.get()
)