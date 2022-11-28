package com.grandemc.fazendas.bukkit.view.craft.select

import com.grandemc.fazendas.bukkit.view.CraftStartView
import com.grandemc.fazendas.bukkit.view.IndustryView
import com.grandemc.fazendas.bukkit.view.craft.start.CraftContext
import com.grandemc.fazendas.config.IndustryConfig
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

class CraftSelectClickHandler : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.craft.select") {
            when (it) {
                "return" -> player.openView(IndustryView::class)
            }
        }

        item.useNBTValueIfPresent<NBTTagByte>(
            NBTReference.VIEW,
            "gfazendas.craft.select.recipe"
        ) {
            player.openView(CraftStartView::class, CraftContext(it.toByte()))
        }
    }
}