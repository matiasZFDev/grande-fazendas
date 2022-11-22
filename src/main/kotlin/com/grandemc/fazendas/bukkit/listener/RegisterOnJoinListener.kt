package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.manager.PlayerManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class RegisterOnJoinListener(
    private val playerManager: PlayerManager
) : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        if (playerManager.hasPlayer(event.player.uniqueId))
            return
        playerManager.registerPlayer(event.player.uniqueId)
    }
}