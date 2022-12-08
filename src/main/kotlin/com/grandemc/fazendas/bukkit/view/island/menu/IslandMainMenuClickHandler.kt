package com.grandemc.fazendas.bukkit.view.island.menu

import com.grandemc.fazendas.bukkit.view.MarketView
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class IslandMainMenuClickHandler(
    private val islandManager: IslandManager
) : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.island.menu") {
            when (it) {
                "market" -> player.openView(MarketView::class)
                "join" -> islandManager.joinIsland(player)
                "leave" -> islandManager.leaveIsland(player)
            }
        }
    }
}