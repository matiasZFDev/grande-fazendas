package com.grandemc.fazendas.registry

import com.grandemc.fazendas.bukkit.listener.FertilizingUsageListener
import com.grandemc.fazendas.bukkit.listener.RegisterOnJoinListener
import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.fazendas.manager.PlayerManager
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

class ListenerRegistry(
    private val plugin: Plugin,
    private val playerManager: PlayerManager,
    private val fertilizingConfig: FertilizingConfig
) {
    private fun register(listener: Listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin)
    }

    fun registerAll() {
        register(RegisterOnJoinListener(playerManager))
        register(FertilizingUsageListener(fertilizingConfig))
    }
}