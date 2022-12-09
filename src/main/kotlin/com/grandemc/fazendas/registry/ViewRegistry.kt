package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.view.*
import com.grandemc.fazendas.bukkit.view.craft.select.CraftSelectPackage
import com.grandemc.fazendas.bukkit.view.craft.start.CraftStartPackage
import com.grandemc.fazendas.bukkit.view.fertilizing.FertilizingPackage
import com.grandemc.fazendas.bukkit.view.hoe.HoePackage
import com.grandemc.fazendas.bukkit.view.industry.IndustryPackage
import com.grandemc.fazendas.bukkit.view.island.IslandPackage
import com.grandemc.fazendas.bukkit.view.island.menu.IslandMainMenuPackage
import com.grandemc.fazendas.bukkit.view.island.top.IslandTopPackage
import com.grandemc.fazendas.bukkit.view.land.LandPackage
import com.grandemc.fazendas.bukkit.view.land_plant.LandPlantPackage
import com.grandemc.fazendas.bukkit.view.lands.LandsPackage
import com.grandemc.fazendas.bukkit.view.market.category.MarketCategoryPackage
import com.grandemc.fazendas.bukkit.view.market.menu.MarketPackage
import com.grandemc.fazendas.bukkit.view.market.purchase.MarketPurchasePackage
import com.grandemc.fazendas.bukkit.view.market.sell.material.MarketSellMaterialPackage
import com.grandemc.fazendas.bukkit.view.market.sell.menu.MarketSellPackage
import com.grandemc.fazendas.bukkit.view.market.selling.MarketSellingPackage
import com.grandemc.fazendas.bukkit.view.market.sold.MarketSoldPackage
import com.grandemc.fazendas.bukkit.view.master.MasterPackage
import com.grandemc.fazendas.bukkit.view.quests.hand_over.QuestHandOverPackage
import com.grandemc.fazendas.bukkit.view.quests.history.QuestHistoryPackage
import com.grandemc.fazendas.bukkit.view.quests.menu.QuestsPackage
import com.grandemc.fazendas.bukkit.view.reward.RewardEditView
import com.grandemc.fazendas.bukkit.view.reward.RewardsView
import com.grandemc.fazendas.bukkit.view.sell.MaterialSellPackage
import com.grandemc.fazendas.bukkit.view.storage.StoragePackage
import com.grandemc.fazendas.bukkit.view.upgrades.UpgradesPackage
import com.grandemc.fazendas.init.model.ConfigCache
import com.grandemc.fazendas.init.model.PluginAPIs
import com.grandemc.fazendas.init.model.PluginManagers
import com.grandemc.fazendas.init.model.PluginStates
import com.grandemc.post.external.lib.manager.view.ViewManager
import com.grandemc.post.external.lib.view.base.ContextData
import com.grandemc.post.external.lib.view.base.View

class ViewRegistry(
    private val viewManager: ViewManager,
    private val managers: PluginManagers,
    private val configs: ConfigCache.Configs,
    private val apis: PluginAPIs,
    private val states: PluginStates
) {
    private fun register(view: View<out ContextData>) {
        viewManager.register(view)
    }

    fun registerAll() {
        register(LandsView(LandsPackage(
            configs.lands, configs.farms, managers.landManager,
            managers.goldBank, managers.storageManager, configs.items,
            managers.islandManager
        )))
        register(LandView(LandPackage(
            managers.landManager, managers.goldBank, configs.farms,
            configs.fertilizing, configs.materials, configs.island,
            managers.islandManager, managers.landPlantManager,
            configs.crops
        )))
        register(LandPlantView(LandPlantPackage(
            configs.items, configs.crops, managers.farmManager,
            managers.landPlantManager, managers.landManager,
            managers.islandManager, managers.statsManager
        )))
        register(FertilizingView(FertilizingPackage(
            configs.fertilizing, configs.farms, managers.landManager,
            configs.items, configs.crops, managers.statsManager,
            managers.islandManager
        )))
        register(HoeView(HoePackage(
            managers.playerManager, configs.farmHoe, managers.goldBank,
            managers.farmItemManager
        )))
        register(IndustryView(IndustryPackage(
            managers.industryManager, configs.industry, configs.materials,
            managers.storageManager
        )))
        register(StorageView(StoragePackage(
            managers.storageManager, configs.materials, configs.items
        )))
        register(CraftSelectView(CraftSelectPackage(
            configs.industry, configs.items, configs.materials
        )))
        register(CraftStartView(CraftStartPackage(
            managers.storageManager, configs.industry,
            managers.industryManager
        )))

        register(MarketView(MarketPackage(
            managers.marketManager, managers.storageManager,
            configs.items
        )))
        register(MarketCategoryView(MarketCategoryPackage(
            managers.marketManager, configs.items, managers.storageManager
        )))
        register(MarketSellView(MarketSellPackage(
            managers.storageManager, apis.conversationFactory,
            managers.marketManager
        )))
        register(MarketSellMaterialView(MarketSellMaterialPackage(
            managers.storageManager, configs.items
        )))
        register(MarketSellingView(MarketSellingPackage(
            managers.marketManager, managers.storageManager,
            configs.items
        )))
        register(MarketPurchaseView(MarketPurchasePackage(
            managers.marketManager, managers.storageManager,
            managers.goldBank, managers.marketSoldItemController
        )))

        register(QuestsView(QuestsPackage(
            managers.questManager, managers.statsManager,
            configs.quests, managers.playerManager
        )))
        register(QuestHandOverView(QuestHandOverPackage(
            managers.questManager, managers.storageManager,
            configs.materials, apis.conversationFactory
        )))
        register(QuestHistoryView(QuestHistoryPackage(
            managers.questManager, managers.farmManager, configs.items
        )))
        register(MaterialSellView(MaterialSellPackage(
            managers.storageManager, apis.conversationFactory,
            managers.goldBank, managers.statsManager
        )))
        register(MasterView(MasterPackage(managers.goldBank)))
        register(IslandView(IslandPackage(
            managers.farmManager, configs.island, managers.playerManager
        )))
        register(IslandTopView(IslandTopPackage(
            states.islandTopState, configs.items
        )))
        register(UpgradesView(UpgradesPackage(
            managers.upgradesManager, configs.items
        )))
        register(MarketSoldView(MarketSoldPackage(
            managers.marketSoldItemController, managers.marketManager,
            managers.storageManager, configs.items, managers.goldBank
        )))
        register(IslandMainMenuView(IslandMainMenuPackage(
            managers.farmManager, managers.landManager, configs.island,
            configs.farms, configs.crops, managers.islandManager
        )))
        register(RewardsView(configs.rewards.get()))
        register(RewardEditView(configs.rewards.get()))
    }
}