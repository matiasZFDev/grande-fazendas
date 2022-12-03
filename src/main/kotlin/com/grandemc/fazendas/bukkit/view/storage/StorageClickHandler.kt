package com.grandemc.fazendas.bukkit.view.storage

import com.grandemc.fazendas.bukkit.view.IndustryView
import com.grandemc.fazendas.bukkit.view.MaterialSellView
import com.grandemc.fazendas.bukkit.view.sell.MaterialSellContext
import com.grandemc.fazendas.global.openView
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.toByte
import com.grandemc.post.external.lib.global.bukkit.nms.useNBTValueIfPresent
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import net.minecraft.server.v1_8_R3.NBTTagByte
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

        item.useNBTValueIfPresent<NBTTagByte>(
            NBTReference.VIEW, "gfazendas.storage.item"
        ) {
            if (event.isShiftClick && event.isRightClick) {
                player.openView(MaterialSellView::class, MaterialSellContext(
                    it.toByte(), 1
                ))
            }
        }
    }
}