package com.grandemc.fazendas.bukkit.view.storage

import com.grandemc.fazendas.bukkit.view.IndustryView
import com.grandemc.fazendas.global.openView
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class StorageClickHandler : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.storage") {
            when (it) {
                "return" -> player.openView(IndustryView::class)
            }
        }
    }
}