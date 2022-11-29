package com.grandemc.fazendas.bukkit.view.market.sell.material

import com.grandemc.fazendas.bukkit.view.MarketSellView
import com.grandemc.fazendas.bukkit.view.market.sell.menu.MarketSellContext
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.toByte
import com.grandemc.post.external.lib.global.bukkit.nms.useNBTValueIfPresent
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MarketSellMaterialClickHandler : ViewClickHandler<MarketSellContext> {
    override fun onClick(player: Player, data: MarketSellContext?, item: ItemStack, event: InventoryClickEvent) {
        requireNotNull(data)
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.market.sell.select") {
            when (it) {
                "return" -> player.openView(MarketSellView::class, data)
            }
        }

        item.useNBTValueIfPresent<NBTTagByte>(
            NBTReference.VIEW, "gfazendas.market.sell.select.material"
        ) {
            player.openView(MarketSellView::class, MarketSellContext(
                it.toByte(),
                data.amount,
                data.price
            ))
            player.respond("mercado_vender.material_setado")
        }
    }
}