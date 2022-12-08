package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.view.craft.select.CraftSelectMenuContainer
import com.grandemc.fazendas.bukkit.view.craft.start.CraftStartMenuContainer
import com.grandemc.fazendas.bukkit.view.fertilizing.FertilizingMenuContainer
import com.grandemc.fazendas.bukkit.view.hoe.HoeMenuContainer
import com.grandemc.fazendas.bukkit.view.industry.IndustryMenuContainer
import com.grandemc.fazendas.bukkit.view.island.IslandMenuContainer
import com.grandemc.fazendas.bukkit.view.island.menu.IslandMainMenuContainer
import com.grandemc.fazendas.bukkit.view.island.top.IslandTopMenuContainer
import com.grandemc.fazendas.bukkit.view.land.LandMenuContainer
import com.grandemc.fazendas.bukkit.view.land_plant.LandPlantMenuContainer
import com.grandemc.fazendas.bukkit.view.lands.LandsMenuContainer
import com.grandemc.fazendas.bukkit.view.market.category.MarketCategoryMenuContainer
import com.grandemc.fazendas.bukkit.view.market.menu.MarketMenuContainer
import com.grandemc.fazendas.bukkit.view.market.purchase.MarketPurchaseMenuContainer
import com.grandemc.fazendas.bukkit.view.market.sell.material.MarketSellMaterialMenuContainer
import com.grandemc.fazendas.bukkit.view.market.sell.menu.MarketSellMenuContainer
import com.grandemc.fazendas.bukkit.view.market.selling.MarketSellingMenuContainer
import com.grandemc.fazendas.bukkit.view.market.sold.MarketSoldMenuContainer
import com.grandemc.fazendas.bukkit.view.master.MasterMenuContainer
import com.grandemc.fazendas.bukkit.view.quests.hand_over.QuestHandOverMenuContainer
import com.grandemc.fazendas.bukkit.view.quests.history.QuestHistoryMenuContainer
import com.grandemc.fazendas.bukkit.view.quests.menu.QuestsMenuContainer
import com.grandemc.fazendas.bukkit.view.sell.MaterialSellMenuContainer
import com.grandemc.fazendas.bukkit.view.storage.StorageMenuContainer
import com.grandemc.fazendas.bukkit.view.upgrades.UpgradesMenuContainer
import com.grandemc.post.external.lib.cache.config.menu.MenuContainer
import com.grandemc.post.external.lib.manager.config.ConfigManager
import com.grandemc.post.external.lib.manager.view.MenuContainerManager

class MenuContainerRegistry(
    private val menuContainerManager: MenuContainerManager,
    private val configManager: ConfigManager,
    private val context: String
) {
    private inline fun <reified T : MenuContainer> register(
        menuConfigName: String,
        section: String? = null
    ) {
        menuContainerManager.register(
            T::class.java.constructors[0].newInstance(
                configManager.getWrapper("menu/$menuConfigName"),
                section,
                context
            ) as MenuContainer
        )
    }

    fun registerAll() {
        register<LandsMenuContainer>("plantios")
        register<LandMenuContainer>("plantio")
        register<LandPlantMenuContainer>("plantar")
        register<FertilizingMenuContainer>("aplicar_fertilizante")
        register<HoeMenuContainer>("enxada")
        register<IndustryMenuContainer>("industria")
        register<StorageMenuContainer>("armazem")
        register<CraftSelectMenuContainer>("escolher_craft")
        register<CraftStartMenuContainer>("iniciar_craft")

        register<MarketMenuContainer>("mercado")
        register<MarketCategoryMenuContainer>("mercado_categoria")
        register<MarketSellMenuContainer>("mercado_vender")
        register<MarketSellMaterialMenuContainer>("mercado_vender_material")
        register<MarketSellingMenuContainer>("mercado_em_venda")
        register<MarketPurchaseMenuContainer>("mercado_comprar")

        register<QuestsMenuContainer>("missoes")
        register<QuestHandOverMenuContainer>("missoes_entrega")
        register<QuestHistoryMenuContainer>("missoes_historia")

        register<MaterialSellMenuContainer>("vender_material")
        register<MasterMenuContainer>("mestre")
        register<IslandMenuContainer>("ilha")
        register<IslandTopMenuContainer>("top")
        register<UpgradesMenuContainer>("melhorias")
        register<MarketSoldMenuContainer>("mercado_vendidos")
        register<IslandMainMenuContainer>("fazenda")
    }
}