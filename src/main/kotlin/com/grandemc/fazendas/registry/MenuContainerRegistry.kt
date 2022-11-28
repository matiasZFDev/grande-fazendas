package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.view.craft.select.CraftSelectMenuContainer
import com.grandemc.fazendas.bukkit.view.craft.start.CraftStartMenuContainer
import com.grandemc.fazendas.bukkit.view.fertilizing.FertilizingMenuContainer
import com.grandemc.fazendas.bukkit.view.hoe.HoeMenuContainer
import com.grandemc.fazendas.bukkit.view.industry.IndustryMenuContainer
import com.grandemc.fazendas.bukkit.view.land.LandMenuContainer
import com.grandemc.fazendas.bukkit.view.land_plant.LandPlantMenuContainer
import com.grandemc.fazendas.bukkit.view.lands.LandsMenuContainer
import com.grandemc.fazendas.bukkit.view.storage.StorageMenuContainer
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
    }
}