package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.view.*
import com.grandemc.fazendas.bukkit.view.fertilizing.FertilizingPackage
import com.grandemc.fazendas.bukkit.view.hoe.HoePackage
import com.grandemc.fazendas.bukkit.view.land.LandPackage
import com.grandemc.fazendas.bukkit.view.land_plant.LandPlantPackage
import com.grandemc.fazendas.bukkit.view.lands.LandsPackage
import com.grandemc.fazendas.init.model.ConfigCache
import com.grandemc.fazendas.init.model.PluginManagers
import com.grandemc.post.external.lib.manager.view.ViewManager
import com.grandemc.post.external.lib.view.base.ContextData
import com.grandemc.post.external.lib.view.base.View

class ViewRegistry(
    private val viewManager: ViewManager,
    private val pluginManagers: PluginManagers,
    private val configs: ConfigCache.Configs
) {
    private fun register(view: View<out ContextData>) {
        viewManager.register(view)
    }

    fun registerAll() {
        register(LandsView(LandsPackage(
            configs.lands, configs.farms, pluginManagers.landManager,
            pluginManagers.goldBank, pluginManagers.storageManager, configs.items
        )))
        register(LandView(LandPackage(
            pluginManagers.landManager, pluginManagers.goldBank, configs.farms,
            configs.fertilizing, configs.materials, configs.island
        )))
        register(LandPlantView(LandPlantPackage(
            configs.items, configs.crops, pluginManagers.farmManager,
            pluginManagers.landPlantManager
        )))
        register(FertilizingView(FertilizingPackage(
            configs.fertilizing, configs.farms, pluginManagers.landManager,
            configs.items, configs.crops
        )))
        register(HoeView(HoePackage(
            pluginManagers.playerManager, configs.farmHoe, pluginManagers.goldBank
        )))
    }
}