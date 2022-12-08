package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.view.*
import com.grandemc.fazendas.bukkit.view.craft.select.CraftSelectPackage
import com.grandemc.fazendas.bukkit.view.craft.start.CraftStartPackage
import com.grandemc.fazendas.bukkit.view.fertilizing.FertilizingPackage
import com.grandemc.fazendas.bukkit.view.hoe.HoePackage
import com.grandemc.fazendas.bukkit.view.industry.IndustryPackage
import com.grandemc.fazendas.bukkit.view.island.IslandPackage
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
    private val pluginManagers: PluginManagers,
    private val configs: ConfigCache.Configs,
    private val apis: PluginAPIs,
    private val states: PluginStates
) {
    private fun register(view: View<out ContextData>) {
        viewManager.register(view)
    }

    fun registerAll() {
        register(LandsView(LandsPackage(
            configs.lands, configs.farms, pluginManagers.landManager,
            pluginManagers.goldBank, pluginManagers.storageManager, configs.items,
            pluginManagers.islandManager
        )))
        register(LandView(LandPackage(
            pluginManagers.landManager, pluginManagers.goldBank, configs.farms,
            configs.fertilizing, configs.materials, configs.island,
            pluginManagers.islandManager, pluginManagers.landPlantManager,
            configs.crops
        )))
        register(LandPlantView(LandPlantPackage(
            configs.items, configs.crops, pluginManagers.farmManager,
            pluginManagers.landPlantManager, pluginManagers.landManager,
            pluginManagers.islandManager, pluginManagers.statsManager
        )))
        register(FertilizingView(FertilizingPackage(
            configs.fertilizing, configs.farms, pluginManagers.landManager,
            configs.items, configs.crops, pluginManagers.statsManager,
            pluginManagers.islandManager
        )))
        register(HoeView(HoePackage(
            pluginManagers.playerManager, configs.farmHoe, pluginManagers.goldBank,
            pluginManagers.farmItemManager
        )))
        register(IndustryView(IndustryPackage(
            pluginManagers.industryManager, configs.industry, configs.materials,
            pluginManagers.storageManager
        )))
        register(StorageView(StoragePackage(
            pluginManagers.storageManager, configs.materials, configs.items
        )))
        register(CraftSelectView(CraftSelectPackage(
            configs.industry, configs.items, configs.materials
        )))
        register(CraftStartView(CraftStartPackage(
            pluginManagers.storageManager, configs.industry,
            pluginManagers.industryManager
        )))

        register(MarketView(MarketPackage(
            pluginManagers.marketManager, pluginManagers.storageManager,
            configs.items
        )))
        register(MarketCategoryView(MarketCategoryPackage(
            pluginManagers.marketManager, configs.items, pluginManagers.storageManager
        )))
        register(MarketSellView(MarketSellPackage(
            pluginManagers.storageManager, apis.conversationFactory,
            pluginManagers.marketManager
        )))
        register(MarketSellMaterialView(MarketSellMaterialPackage(
            pluginManagers.storageManager, configs.items
        )))
        register(MarketSellingView(MarketSellingPackage(
            pluginManagers.marketManager, pluginManagers.storageManager,
            configs.items
        )))
        register(MarketPurchaseView(MarketPurchasePackage(
            pluginManagers.marketManager, pluginManagers.storageManager,
            pluginManagers.goldBank, pluginManagers.marketSoldItemController
        )))

        register(QuestsView(QuestsPackage(
            pluginManagers.questManager, pluginManagers.statsManager,
            configs.quests, pluginManagers.playerManager
        )))
        register(QuestHandOverView(QuestHandOverPackage(
            pluginManagers.questManager, pluginManagers.storageManager,
            configs.materials, apis.conversationFactory
        )))
        register(QuestHistoryView(QuestHistoryPackage(
            pluginManagers.questManager, pluginManagers.farmManager, configs.items
        )))
        register(MaterialSellView(MaterialSellPackage(
            pluginManagers.storageManager, apis.conversationFactory,
            pluginManagers.goldBank, pluginManagers.statsManager
        )))
        register(MasterView(MasterPackage(pluginManagers.goldBank)))
        register(IslandView(IslandPackage(
            pluginManagers.farmManager, configs.island, pluginManagers.playerManager
        )))
        register(IslandTopView(IslandTopPackage(
            states.islandTopState, configs.items
        )))
        register(UpgradesView(UpgradesPackage(
            pluginManagers.upgradesManager, configs.items
        )))
        register(MarketSoldView(MarketSoldPackage(
            pluginManagers.marketSoldItemController, pluginManagers.marketManager,
            pluginManagers.storageManager, configs.items, pluginManagers.goldBank
        )))
    }
}