package com.grandemc.fazendas.bukkit.view.market.selling

import com.grandemc.fazendas.bukkit.view.MarketSellingView
import com.grandemc.fazendas.bukkit.view.MarketView
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.global.bukkit.nms.*
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import net.minecraft.server.v1_8_R3.NBTTagInt
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MarketSellingClickHandler(
    private val marketManager: MarketManager,
    private val storageManager: StorageManager
) : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.market.selling") {
            when (it) {
                "return" -> player.openView(MarketView::class)
            }
        }

        item.useNBTValueIfPresent<NBTTagInt>(
            NBTReference.VIEW, "gfazendas.market.selling.product"
        ) {
            val product = marketManager.getProduct(it.toInt())!!
            marketManager.removeProduct(it.toInt())
            storageManager.deposit(player.uniqueId, product.itemId, product.amount)
            player.openView(MarketSellingView::class)
            player.respond("mercado.produto_retirado")
        }
    }
}