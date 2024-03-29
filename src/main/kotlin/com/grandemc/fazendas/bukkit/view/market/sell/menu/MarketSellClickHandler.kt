package com.grandemc.fazendas.bukkit.view.market.sell.menu

import com.grandemc.fazendas.bukkit.conversation.MarketProductAmountConversation
import com.grandemc.fazendas.bukkit.conversation.MarketSellPriceConversation
import com.grandemc.fazendas.bukkit.event.MarketPostEvent
import com.grandemc.fazendas.bukkit.view.MarketSellMaterialView
import com.grandemc.fazendas.bukkit.view.MarketView
import com.grandemc.fazendas.bukkit.view.PageContext
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.MarketManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.global.bukkit.converse
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.global.callEvent
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.conversations.ConversationFactory
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MarketSellClickHandler(
    private val conversationFactory: ConversationFactory,
    private val marketManager: MarketManager,
    private val storageManager: StorageManager
) : ViewClickHandler<MarketSellContext> {
    override fun onClick(player: Player, data: MarketSellContext?, item: ItemStack, event: InventoryClickEvent) {
        requireNotNull(data)
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.market.sell") {
            when (it) {
                "return" -> player.openView(MarketView::class)
                "material" -> player.openView(
                    MarketSellMaterialView::class,
                    data
                )
                "amount" -> player.converse(
                    conversationFactory,
                    MarketProductAmountConversation(data)
                )
                "price" -> player.converse(
                    conversationFactory,
                    MarketSellPriceConversation(data)
                )
                "post" -> {
                    val playerProducts = marketManager.getPlayerProducts(
                        player.uniqueId
                    )

                    if (marketManager.sellingLimit().toInt() == playerProducts.size) {
                        player.closeInventory()
                        player.respond("mercado_vender.limite_a_venda")
                        return@useReferenceIfPresent
                    }

                    requireNotNull(data.materialId)

                    val materialConfig = storageManager.materialData(
                        data.materialId
                    )

                    storageManager.withdraw(
                        player.uniqueId, data.materialId, data.amount
                    )
                    marketManager.postItem(
                        player.uniqueId,
                        data.materialId,
                        data.amount,
                        data.price
                    )
                    callEvent(MarketPostEvent(player.uniqueId))
                    player.closeInventory()
                    player.respond("mercado_vender.vendido") {
                        replace(
                            "{material}" to materialConfig.name,
                            "{quantia}" to data.amount.commaFormat(),
                            "{preco}" to data.price.toFormat()
                        )
                    }
                }
            }
        }
    }
}