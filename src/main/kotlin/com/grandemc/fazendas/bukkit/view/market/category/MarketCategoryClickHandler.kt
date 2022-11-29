package com.grandemc.fazendas.bukkit.view.market.category

import com.grandemc.fazendas.bukkit.view.MarketCategoryView
import com.grandemc.fazendas.bukkit.view.MarketPurchaseView
import com.grandemc.fazendas.bukkit.view.market.purchase.MarketPurchaseContext
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.toInt
import com.grandemc.post.external.lib.global.bukkit.nms.useNBTValueIfPresent
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import net.minecraft.server.v1_8_R3.NBTTagInt
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MarketCategoryClickHandler(
    private val marketManager: MarketManager
) : ViewClickHandler<MarketCategoryContext> {
    override fun onClick(player: Player, data: MarketCategoryContext?, item: ItemStack, event: InventoryClickEvent) {
        requireNotNull(data)
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.market.category") {
            when (it) {
                "next" -> player.openView(
                    MarketCategoryView::class,
                    MarketCategoryContext(data.categoryId, data.page.inc(), data.filter)
                )
                "previous" -> player.openView(
                    MarketCategoryView::class,
                    MarketCategoryContext(data.categoryId, data.page.dec(), data.filter)
                )
                "filter" -> {
                    if (event.isLeftClick)
                        player.openView(
                            MarketCategoryView::class,
                            MarketCategoryContext(
                                data.categoryId, data.page, data.filter.next()
                            )
                        )

                    if (event.isRightClick)
                        player.openView(
                            MarketCategoryView::class,
                            MarketCategoryContext(
                                data.categoryId, data.page, data.filter.opposite()
                            )
                        )
                }
            }
        }

        item.useNBTValueIfPresent<NBTTagInt>(
            NBTReference.VIEW, "gfazendas.market.category.product"
        ) {
            val productId = it.toInt()
            val product = marketManager.getProduct(productId)

            if (product == null || product.sellerId == player.uniqueId) {
                player.respond("mercado.produto_invalido")
                player.openView(MarketCategoryView::class, data)
                return@useNBTValueIfPresent
            }

            player.openView(MarketPurchaseView::class, MarketPurchaseContext(
                product,
                data
            ))
        }
    }
}