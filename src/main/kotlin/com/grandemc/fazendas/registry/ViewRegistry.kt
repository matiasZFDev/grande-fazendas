package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.view.*
import com.grandemc.fazendas.bukkit.view.craft.select.CraftSelectPackage
import com.grandemc.fazendas.bukkit.view.craft.start.CraftStartPackage
import com.grandemc.fazendas.bukkit.view.fertilizing.FertilizingPackage
import com.grandemc.fazendas.bukkit.view.hoe.HoePackage
import com.grandemc.fazendas.bukkit.view.industry.IndustryPackage
import com.grandemc.fazendas.bukkit.view.land.LandPackage
import com.grandemc.fazendas.bukkit.view.land_plant.LandPlantPackage
import com.grandemc.fazendas.bukkit.view.lands.LandsPackage
import com.grandemc.fazendas.bukkit.view.market.category.MarketCategoryPackage
import com.grandemc.fazendas.bukkit.view.market.menu.MarketPackage
import com.grandemc.fazendas.bukkit.view.market.purchase.MarketPurchasePackage
import com.grandemc.fazendas.bukkit.view.market.sell.material.MarketSellMaterialPackage
import com.grandemc.fazendas.bukkit.view.market.sell.menu.MarketSellPackage
import com.grandemc.fazendas.bukkit.view.market.selling.MarketSellingPackage
import com.grandemc.fazendas.bukkit.view.quests.hand_over.QuestHandOverPackage
import com.grandemc.fazendas.bukkit.view.quests.history.QuestHistoryPackage
import com.grandemc.fazendas.bukkit.view.quests.menu.QuestsPackage
import com.grandemc.fazendas.bukkit.view.storage.StoragePackage
import com.grandemc.fazendas.init.model.ConfigCache
import com.grandemc.fazendas.init.model.PluginAPIs
import com.grandemc.fazendas.init.model.PluginManagers
import com.grandemc.post.external.lib.manager.view.ViewManager
import com.grandemc.post.external.lib.view.base.ContextData
import com.grandemc.post.external.lib.view.base.View

class ViewRegistry(
    private val viewManager: ViewManager,
    private val pluginManagers: PluginManagers,
    private val configs: ConfigCache.Configs,
    private val apis: PluginAPIs
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
            configs.fertilizing, configs.materials, configs.island
        )))
        register(LandPlantView(LandPlantPackage(
            configs.items, configs.crops, pluginManagers.farmManager,
            pluginManagers.landPlantManager, pluginManagers.islandManager
        )))
        register(FertilizingView(FertilizingPackage(
            configs.fertilizing, configs.farms, pluginManagers.landManager,
            configs.items, configs.crops
        )))
        register(HoeView(HoePackage(
            pluginManagers.playerManager, configs.farmHoe, pluginManagers.goldBank
        )))
        register(IndustryView(IndustryPackage(
            pluginManagers.industryManager, configs.industry, configs.materials,
            pluginManagers.storageManager
        )))
        register(StorageView(StoragePackage(
            pluginManagers.storageManager, configs.storage, configs.materials,
            configs.items
        )))
        register(CraftSelectView(CraftSelectPackage(
            configs.industry, configs.items, configs.materials
        )))
        register(CraftStartView(CraftStartPackage(
            pluginManagers.storageManager, configs.industry,
            pluginManagers.industryManager
        )))

        register(MarketView(MarketPackage(
            pluginManagers.marketManager, configs.market, pluginManagers.storageManager,
            configs.items
        )))
        register(MarketCategoryView(MarketCategoryPackage(
            pluginManagers.marketManager, configs.market, configs.items,
            pluginManagers.storageManager
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
            pluginManagers.goldBank
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
    }
}