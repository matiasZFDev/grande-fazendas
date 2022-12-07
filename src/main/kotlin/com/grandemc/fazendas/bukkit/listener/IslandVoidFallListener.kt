package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.IslandLocationManager
import com.grandemc.fazendas.manager.IslandManager
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class IslandVoidFallListener(
    private val islandManager: IslandManager,
    private val locationManager: IslandLocationManager
) : Listener {
    @EventHandler(ignoreCancelled = true)
    fun onVoidFall(event: EntityDamageEvent) {
        if (event.cause != EntityDamageEvent.DamageCause.VOID)
            return

        if (event.entity !is Player)
            return

        if (!islandManager.insideIsland(event.entity.uniqueId))
            return

        event.isCancelled = true
        val islandSpawn = locationManager.islandSpawn(event.entity.uniqueId)
        event.entity.respond("ilha.queda")
        event.entity.teleport(islandSpawn)
    }
}