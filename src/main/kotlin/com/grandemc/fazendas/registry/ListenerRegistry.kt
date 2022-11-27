package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.listener.*
import com.grandemc.fazendas.init.model.ConfigCache
import com.grandemc.fazendas.init.model.PluginManagers
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

class ListenerRegistry(
    private val plugin: Plugin,
    private val configs: ConfigCache.Configs,
    private val managers: PluginManagers
) {
    private fun register(listener: Listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin)
    }

    fun registerAll() {
        register(RegisterOnJoinListener(managers.playerManager))
        register(FertilizingUsageListener(configs.fertilizing))
        register(LootBoxOpenListener(configs.lootBox, managers.farmItemManager))
        register(IslandInteractionListener(configs.island))
        register(CropCollectListener(
            configs.island, managers.islandManager, managers.landManager,
            configs.crops, managers.storageManager, managers.farmItemManager,
            managers.playerManager
        ))
    }
}