package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.config.IslandConfig
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.hanging.HangingBreakEvent
import org.bukkit.event.hanging.HangingPlaceEvent
import org.bukkit.event.player.PlayerArmorStandManipulateEvent
import org.bukkit.event.player.PlayerBucketEmptyEvent
import org.bukkit.event.player.PlayerBucketFillEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent

class IslandInteractionListener(private val islandConfig: IslandConfig) : Listener {
    @EventHandler
    fun onBreak(event: BlockBreakEvent) {
        if (event.block.world.name != islandConfig.get().worldName)
            return
        event.isCancelled = true
    }

    @EventHandler
    fun onPlace(event: BlockPlaceEvent) {
        if (event.block.world.name != islandConfig.get().worldName)
            return
        event.isCancelled = true
    }

    @EventHandler
    fun onBucketEmpty(event: PlayerBucketEmptyEvent) {
        if (event.blockClicked.world.name != islandConfig.get().worldName)
            return
        event.isCancelled = true
    }

    @EventHandler
    fun onBucketFill(event: PlayerBucketFillEvent) {
        if (event.blockClicked.world.name != islandConfig.get().worldName)
            return
        event.isCancelled = true
    }

    @EventHandler
    fun onArmorStandManipulation(event: PlayerArmorStandManipulateEvent) {
        if (event.rightClicked.world.name != islandConfig.get().worldName)
            return
        event.isCancelled = true
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (event.player.world.name != islandConfig.get().worldName)
            return
        event.isCancelled = true
    }

    @EventHandler
    fun onEntityInteract(event: PlayerInteractEntityEvent) {
        if (event.player.world.name != islandConfig.get().worldName)
            return
        if (event.rightClicked.type == EntityType.PLAYER)
            return
        event.isCancelled = true
    }

    @EventHandler
    fun onDamage(event: EntityDamageByEntityEvent) {
        if (event.damager.world.name != islandConfig.get().worldName)
            return
        event.isCancelled = true
    }

    @EventHandler
    fun onHangingBreak(event: HangingBreakEvent) {
        if (event.entity.world.name != islandConfig.get().worldName)
            return
        event.isCancelled = true
    }

    @EventHandler
    fun onHangingPlace(event: HangingPlaceEvent) {
        if (event.entity.world.name != islandConfig.get().worldName)
            return
        event.isCancelled = true
    }
}