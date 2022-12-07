package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.getCommand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class IslandCommandListener(private val islandConfig: IslandConfig) : Listener {
    @EventHandler
    fun onCommand(event: PlayerCommandPreprocessEvent) {
        val command = event.getCommand().also {
            if (it.isEmpty())
                return
        }

        if (!islandConfig.get().commandWhitelist.contains(command))
            return

        event.isCancelled = true
    }
}