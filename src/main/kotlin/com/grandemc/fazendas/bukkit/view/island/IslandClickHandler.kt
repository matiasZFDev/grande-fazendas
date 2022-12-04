package com.grandemc.fazendas.bukkit.view.island

import com.grandemc.fazendas.bukkit.view.IslandView
import com.grandemc.fazendas.bukkit.view.MasterView
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Location
import org.bukkit.entity.Firework
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class IslandClickHandler(
    private val farmManager: FarmManager
) : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.island") {
            when (it) {
                "return" -> player.openView(MasterView::class)
                "evolve" -> {
                    val island = farmManager.farm(player.uniqueId)
                    island.levelUp()
                    player.openView(IslandView::class)
                    launchFirework(player.location)
                    player.respond("fazenda.upada")
                }
            }
        }
    }

    private fun launchFirework(location: Location) {
        val firework = location.world.spawn(location, Firework::class.java) as Firework
        val meta = firework.fireworkMeta
        val effect = FireworkEffect.builder()
            .trail(true)
            .with(FireworkEffect.Type.STAR)
            .withColor(Color.AQUA)
            .build()
        meta.addEffect(effect)
        meta.power = 1
        firework.fireworkMeta = meta
    }
}