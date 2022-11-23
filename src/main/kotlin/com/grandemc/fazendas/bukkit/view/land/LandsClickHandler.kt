package com.grandemc.fazendas.bukkit.view.land

import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class LandsClickHandler : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.lands") {
            when (it) {
                "purchasable" -> {}
                "info" -> {}
            }
        }
    }
}