package com.grandemc.fazendas.bukkit.view.industry

import com.grandemc.fazendas.bukkit.view.CraftSelectView
import com.grandemc.fazendas.bukkit.view.StorageView
import com.grandemc.fazendas.global.openView
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class IndustryClickHandler : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.industry") {
            when (it) {
                "storage" -> player.openView(StorageView::class)
                "craft" -> player.openView(CraftSelectView::class)
            }
        }
    }
}