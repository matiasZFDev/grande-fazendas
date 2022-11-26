package com.grandemc.fazendas.bukkit.view.land

import com.grandemc.fazendas.bukkit.view.LandPlantView
import com.grandemc.fazendas.global.openView
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class LandClickHandler : ViewClickHandler<LandContext> {
    override fun onClick(player: Player, data: LandContext?, item: ItemStack, event: InventoryClickEvent) {
        requireNotNull(data)
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.land") {
            when (it) {
                "plant" -> player.openView(LandPlantView::class, data)
            }
        }
    }
}