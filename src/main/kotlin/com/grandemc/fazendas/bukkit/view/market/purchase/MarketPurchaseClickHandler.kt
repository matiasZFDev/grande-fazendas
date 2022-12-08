package com.grandemc.fazendas.bukkit.view.market.purchase

import com.grandemc.fazendas.bukkit.event.MarketBuyEvent
import com.grandemc.fazendas.bukkit.event.MarketSellEvent
import com.grandemc.fazendas.bukkit.view.MarketCategoryView
import com.grandemc.fazendas.bukkit.view.MarketSellingView
import com.grandemc.fazendas.global.*
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.global.*
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.global.bukkit.offlinePlayer
import com.grandemc.post.external.lib.global.bukkit.runIfOnline
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MarketPurchaseClickHandler(
    private val marketManager: MarketManager,
    private val storageManager: StorageManager,
    private val goldBank: GoldBank
) : ViewClickHandler<MarketPurchaseContext> {
    override fun onClick(
        player: Player, data: MarketPurchaseContext?, item: ItemStack, event: InventoryClickEvent
    ) {
        requireNotNull(data)

        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.market.purchase") {
            when (it) {
                "cancelar" -> {
                    player.openView(MarketCategoryView::class, data.categoryContext)
                    player.respond("mercado.compra_cancelada")
                }
                "confirm" -> {
                    val product = marketManager.getProduct(data.product.id())
                    val isProductInvalid =
                        product == null ||
                        product.sellerId == player.uniqueId ||
                        product !== data.product

                    if (isProductInvalid) {
                        player.respond("mercado.produto_invalido")
                        player.openView(MarketCategoryView::class, data.categoryContext)
                        return@useReferenceIfPresent
                    }

                    if (!goldBank.has(player.uniqueId, product!!.goldPrice)) {
                        player.closeInventory()
                        player.respond("mercado.comprar_ouro")
                        return@useReferenceIfPresent
                    }

                    val goldWithTax = product.goldPrice.applyPercentage(
                        marketManager.tax(), ApplyType.DECREMENT
                    )
                    val materialConfig = storageManager.materialData(
                        data.product.itemId
                    )
                    marketManager.removeProduct(product.id())
                    goldBank.withdraw(player.uniqueId, product.goldPrice)
                    goldBank.deposit(product.sellerId, goldWithTax)
                    storageManager.deposit(player.uniqueId, product.itemId, product.amount)

                    callEvent(MarketBuyEvent(player.uniqueId))
                    callEvent(MarketSellEvent(product.sellerId))

                    player.closeInventory()
                    player.respond("mercado.produto_comprado_comprador") {
                        replace(
                            "{material}" to materialConfig.name,
                            "{quantia}" to data.product.amount.commaFormat(),
                            "{preco}" to data.product.goldPrice.toFormat(),
                            "{vendedor}" to data.product.sellerId.offlinePlayer().name
                        )
                    }

                    product.sellerId.runIfOnline {
                        respond("mercado.produto_comprado_vendedor") {
                            replace(
                                "{material}" to materialConfig.name,
                                "{quantia}" to data.product.amount.commaFormat(),
                                "{preco}" to data.product.goldPrice.toFormat(),
                                "{taxa}" to marketManager.tax().intFormat(),
                                "{preco_taxa}" to goldWithTax.toFormat()
                            )
                        }
                        updateView<MarketSellingView>()
                    }
                }
            }
        }
    }
}