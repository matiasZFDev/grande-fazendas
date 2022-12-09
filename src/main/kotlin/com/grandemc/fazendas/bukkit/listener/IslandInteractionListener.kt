package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.IslandLocationManager
import com.grandemc.fazendas.manager.IslandManager
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.ItemSpawnEvent
import org.bukkit.event.hanging.HangingBreakEvent
import org.bukkit.event.hanging.HangingPlaceEvent
import org.bukkit.event.player.PlayerArmorStandManipulateEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.player.PlayerBucketFillEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerTeleportEvent

class IslandInteractionListener(
    private val islandManager: IslandManager,
    private val locationManager: IslandLocationManager
) : Listener {
    @EventHandler
    fun onItemSpawn(event: ItemSpawnEvent) {
        if (event.entity.world.name != islandManager.islandWorld())
            return

        event.isCancelled = true
    }

    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        if (event.player.hasPermission("grandemc.bypass"))
            return

        if (!islandManager.insideIsland(event.player.uniqueId))
            return

        event.isCancelled = true
    }

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        if (event.player.hasPermission("grandemc.bypass"))
            return

        if (!islandManager.insideIsland(event.player.uniqueId))
            return

        event.isCancelled = true
    }

    @EventHandler
    fun onBucketEmpty(event: PlayerBucketEmptyEvent) {
        if (!islandManager.insideIsland(event.player.uniqueId))
            return

        event.isCancelled = true
    }

    @EventHandler
    fun onBucketFill(event: PlayerBucketFillEvent) {
        if (!islandManager.insideIsland(event.player.uniqueId))
            return

        event.isCancelled = true
    }

    @EventHandler
    fun onArmorStandManipulation(event: PlayerArmorStandManipulateEvent) {
        if (!islandManager.insideIsland(event.player.uniqueId))
            return

        event.isCancelled = true
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (!islandManager.insideIsland(event.player.uniqueId))
            return

        event.isCancelled = true
    }

    @EventHandler
    fun onEntityInteract(event: PlayerInteractEntityEvent) {
        if (!islandManager.insideIsland(event.player.uniqueId))
            return

        if (event.rightClicked.type == EntityType.PLAYER)
            return

        event.isCancelled = true
    }

    @EventHandler
    fun onDamage(event: EntityDamageEvent) {
        if (event.entity.world.name != islandManager.islandWorld())
            return

        if (event.entity !is Player)
            return

        event.isCancelled = true

        if (event.cause == EntityDamageEvent.DamageCause.VOID) {
            val islandSpawn = locationManager.islandSpawn(event.entity.uniqueId)
            event.entity.respond("ilha.queda")
            event.entity.teleport(
                islandSpawn,
                PlayerTeleportEvent.TeleportCause.END_PORTAL
            )
            return
        }
    }

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        if (event.entity.world.name != islandManager.islandWorld())
            return

        if (event.damager is Player) {
            if (islandManager.insideIsland(event.damager.uniqueId)) {
                event.isCancelled = true
                return
            }
        }

        if (event.entity is Player) {
            if (islandManager.insideIsland(event.entity.uniqueId)) {
                event.isCancelled = true
                return
            }
        }
    }

    @EventHandler
    fun onHangingBreak(event: HangingBreakEvent) {
        if (event.entity.world.name != islandManager.islandWorld())
            return
        event.isCancelled = true
    }

    @EventHandler
    fun onHangingPlace(event: HangingPlaceEvent) {
        if (event.entity.world.name != islandManager.islandWorld())
            return
        event.isCancelled = true
    }
}