package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.manager.IslandManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

class IslandDropListener(
    private val islandManager: IslandManager
) : Listener {
    @EventHandler
    fun onDrop(event: PlayerDropItemEvent) {
        if (!islandManager.insideIsland(event.player.uniqueId))
            return

        event.isCancelled = true
    }
}