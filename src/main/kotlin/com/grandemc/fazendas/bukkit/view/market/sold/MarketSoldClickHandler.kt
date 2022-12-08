package com.grandemc.fazendas.bukkit.view.market.sold

import com.grandemc.fazendas.bukkit.view.MarketSoldView
import com.grandemc.fazendas.bukkit.view.MarketView
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.controller.MarketSoldItemController
import com.grandemc.post.external.lib.global.ApplyType
import com.grandemc.post.external.lib.global.applyPercentage
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.toInt
import com.grandemc.post.external.lib.global.bukkit.nms.useNBTValueIfPresent
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import net.minecraft.server.v1_8_R3.NBTTagInt
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MarketSoldClickHandler(
    private val soldItemController: MarketSoldItemController,
    private val marketManager: MarketManager,
    private val goldBank: GoldBank
) : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.market.sold") {
            when (it) {
                "return" -> player.openView(MarketView::class)
            }
        }

        item.useNBTValueIfPresent<NBTTagInt>(
            NBTReference.VIEW, "gfazendas.market.sold.product"
        ) {
            val product = soldItemController.collectItem(it.toInt())
            val goldWithTax = product.goldPrice.applyPercentage(
                marketManager.tax(), ApplyType.DECREMENT
            )
            goldBank.deposit(player.uniqueId, goldWithTax)
            player.openView(MarketSoldView::class)
            player.respond("mercado.venda_colhida") {
                replace(
                    "{ouro}" to goldWithTax.toFormat()
                )
            }
        }
    }
}