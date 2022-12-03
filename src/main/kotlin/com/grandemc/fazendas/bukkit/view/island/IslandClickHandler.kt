package com.grandemc.fazendas.bukkit.view.island

import com.grandemc.fazendas.bukkit.view.IslandView
import com.grandemc.fazendas.bukkit.view.MasterView
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
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
                    player.respond("fazenda.upada")
                }
            }
        }
    }
}