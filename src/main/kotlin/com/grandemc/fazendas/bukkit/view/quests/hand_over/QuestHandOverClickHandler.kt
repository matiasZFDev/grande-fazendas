package com.grandemc.fazendas.bukkit.view.quests.hand_over

import com.grandemc.fazendas.bukkit.conversation.QuestHandOverAmountConversation
import com.grandemc.fazendas.bukkit.event.MaterialHandOverEvent
import com.grandemc.fazendas.bukkit.view.QuestHandOverView
import com.grandemc.fazendas.bukkit.view.QuestsView
import com.grandemc.fazendas.config.model.quest.type.HandOverQuest
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.global.bukkit.converse
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.global.callEvent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.Bukkit
import org.bukkit.conversations.ConversationFactory
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class QuestHandOverClickHandler(
    private val questManager: QuestManager,
    private val storageManager: StorageManager,
    private val conversationFactory: ConversationFactory
) : ViewClickHandler<HandOverContext> {
    override fun onClick(
        player: Player, data: HandOverContext?, item: ItemStack, event: InventoryClickEvent
    ) {
        requireNotNull(data)
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.quests.hand_over") {
            when (it) {
                "return" -> player.openView(QuestsView::class)
                "amount" -> player.converse(
                    conversationFactory,
                    QuestHandOverAmountConversation(data)
                )
                "offer" -> {
                    val quest = questManager.currentQuest(player.uniqueId)!!
                    val questConfig = questManager.questConfig(quest.id()) as HandOverQuest
                    val materialId = questConfig.materialId()
                    storageManager.withdraw(player.uniqueId, materialId, data.amount)
                    player.respond("oferenda.oferecido")
                    callEvent(MaterialHandOverEvent(player.uniqueId, materialId, data.amount))

                    if (quest.isDone())
                        player.openView(QuestsView::class)
                    else
                        player.openView(QuestHandOverView::class)
                }
            }
        }
    }
}