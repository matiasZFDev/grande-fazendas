package com.grandemc.fazendas.bukkit.view.master

import com.grandemc.fazendas.bukkit.view.*
import com.grandemc.fazendas.global.openView
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MasterClickHandler : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.master") {
            when (it) {
                "quests" -> player.openView(QuestsView::class)
                "market" -> player.openView(MarketView::class)
                "island" -> player.openView(IslandView::class)
                "top" -> player.openView(IslandTopView::class)
                "upgrades" -> player.openView(UpgradesView::class)
            }
        }
    }
}