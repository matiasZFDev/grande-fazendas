package com.grandemc.fazendas.bukkit.view.sell

import com.grandemc.fazendas.bukkit.conversation.MaterialSellAmountConversation
import com.grandemc.fazendas.bukkit.view.StorageView
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.StatsManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.global.bukkit.converse
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.conversations.ConversationFactory
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class MaterialSellClickHandler(
    private val conversationFactory: ConversationFactory,
    private val storageManager: StorageManager,
    private val goldBank: GoldBank,
    private val statsManager: StatsManager
) : ViewClickHandler<MaterialSellContext> {
    override fun onClick(
        player: Player, data: MaterialSellContext?, item: ItemStack, event: InventoryClickEvent
    ) {
        requireNotNull(data)
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.storage.sell") {
            when (it) {
                "return" -> player.openView(StorageView::class)
                "amount" -> player.converse(
                    conversationFactory, MaterialSellAmountConversation(data)
                )
                "sell" -> {
                    val materialConfig = storageManager.materialData(data.materialId)
                    val goldPrice = materialConfig.goldPrice * data.amount
                    val boostedGold = statsManager.localSell(player.uniqueId, goldPrice)
                    storageManager.withdraw(
                        player.uniqueId, data.materialId, data.amount
                    )
                    goldBank.deposit(player.uniqueId, boostedGold)
                    player.closeInventory()
                    player.respond("armazem.iten_vendido") {
                        replace(
                            "{material}" to materialConfig.name,
                            "{quantia}" to data.amount.commaFormat(),
                            "{ouro}" to boostedGold.toFormat()
                        )
                    }
                }
            }
        }
    }
}