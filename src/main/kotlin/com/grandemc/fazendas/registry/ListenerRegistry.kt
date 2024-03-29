package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.listener.*
import com.grandemc.fazendas.bukkit.listener.quest.*
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
        register(FertilizingUsageListener(
            configs.fertilizing, managers.islandManager
        ))
        register(LootBoxOpenListener(configs.lootBox, managers.farmItemManager))
        register(IslandInteractionListener(
            managers.islandManager, managers.locationManager
        ))
        register(FarmHoeCollectListener(
            configs.island, managers.locationManager, managers.landManager,
            configs.crops, managers.storageManager, managers.farmItemManager,
            managers.playerManager, configs.farmHoe, configs.lootBox,
            managers.islandManager, managers.statsManager, managers.farmManager
        ))
        register(FarmHoeMenuOpenListener())
        register(IslandCommandListener(configs.island, managers.islandManager))
        register(IslandLeaveListener(managers.islandManager))
        register(IslandPlayerTeleportListener(managers.islandManager))
        register(BoosterConsumeListener(managers.playerManager))
        questListeners()
    }

    private fun questListeners() {
        register(QuestCompleteListener(
            managers.questManager, managers.farmManager, managers.statsManager
        ))
        register(CropCollectListener(managers.questManager))
        register(XpGainListener(managers.questManager))
        register(FarmPlantListener(managers.questManager))
        register(IndustryCraftListener(managers.questManager))
        register(MarketPostListener(managers.questManager))
        register(MarketSellListener(managers.questManager))
        register(MarketBuyListener(managers.questManager))
        register(MaterialHandOverListener(managers.questManager))
        register(IslandDropListener(managers.islandManager))
    }
}