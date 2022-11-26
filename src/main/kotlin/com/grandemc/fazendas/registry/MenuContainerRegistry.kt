package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.view.land.LandMenuContainer
import com.grandemc.fazendas.bukkit.view.land_plant.LandPlantMenuContainer
import com.grandemc.fazendas.bukkit.view.lands.LandsMenuContainer
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
    }
}