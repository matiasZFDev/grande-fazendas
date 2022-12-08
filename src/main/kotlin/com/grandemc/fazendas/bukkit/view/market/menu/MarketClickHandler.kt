package com.grandemc.fazendas.bukkit.view.market.menu

import com.grandemc.fazendas.bukkit.view.*
import com.grandemc.fazendas.bukkit.view.market.category.MarketCategoryContext
import com.grandemc.fazendas.bukkit.view.market.sell.menu.MarketSellContext
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.manager.model.MarketFilter
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.toByte
import com.grandemc.post.external.lib.global.bukkit.nms.useNBTValueIfPresent
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MarketClickHandler : ViewClickHandler<PageContext> {
    override fun onClick(player: Player, data: PageContext?, item: ItemStack, event: InventoryClickEvent) {
        requireNotNull(data)
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.market") {
            when (it) {
                "next" -> player.openView(MarketView::class, PageContext(data.page.inc()))
                "previous" -> player.openView(MarketView::class, PageContext(data.page.dec()))
                "sell" -> player.openView(
                    MarketSellView::class,
                    MarketSellContext(null, 1, 100.0)
                )
                "selling" -> player.openView(MarketSellingView::class)
                "sold" -> player.openView(MarketSoldView::class)
            }
        }
        item.useNBTValueIfPresent<NBTTagByte>(
            NBTReference.VIEW,
            "gfazendas.market.category"
        ) {
            val categoryId = it.toByte()
            player.openView(MarketCategoryView::class, MarketCategoryContext(
                categoryId, data.page, MarketFilter.GREATER_AMOUNT
            ))
        }
    }
}