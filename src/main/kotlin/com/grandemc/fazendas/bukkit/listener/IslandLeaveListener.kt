package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.manager.IslandManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

class IslandLeaveListener(private val islandManager: IslandManager) : Listener {
    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        if (!islandManager.insideIsland(event.player.uniqueId))
            return
        islandManager.leaveIsland(event.player)
    }

    @EventHandler
    fun onKick(event: PlayerKickEvent) {
        if (!islandManager.insideIsland(event.player.uniqueId))
            return
        islandManager.leaveIsland(event.player)
    }

}