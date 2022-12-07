package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.getCommand
import com.grandemc.fazendas.manager.IslandManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class IslandCommandListener(
    private val islandConfig: IslandConfig,
    private val islandManager: IslandManager
) : Listener {
    @EventHandler
    fun onCommand(event: PlayerCommandPreprocessEvent) {
        if (!islandManager.insideIsland(event.player.uniqueId))
            return

        val command = event.getCommand().also {
            if (it.isEmpty())
                return
        }

        if (command == islandConfig.get().leaveCommand) {
            islandManager.leaveIsland(event.player, false)
            return
        }


        if (!islandConfig.get().commandWhitelist.contains(command)) {
            event.isCancelled = true
            return
        }
    }
}