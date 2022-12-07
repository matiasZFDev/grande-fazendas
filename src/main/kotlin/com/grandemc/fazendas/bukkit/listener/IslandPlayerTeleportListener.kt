package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.manager.IslandManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerTeleportEvent

class IslandPlayerTeleportListener(
    private val islandManager: IslandManager
) : Listener {
    @EventHandler
    fun onTeleport(event: PlayerTeleportEvent) {
        if (!islandManager.insideIsland(event.player.uniqueId))
            return

        event.isCancelled = true
    }
}