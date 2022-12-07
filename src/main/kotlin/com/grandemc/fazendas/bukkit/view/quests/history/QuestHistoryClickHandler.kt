package com.grandemc.fazendas.bukkit.view.quests.history

import com.grandemc.fazendas.bukkit.view.PageContext
import com.grandemc.fazendas.bukkit.view.QuestHistoryView
import com.grandemc.fazendas.bukkit.view.QuestsView
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.QuestManager
import com.grandemc.fazendas.storage.player.model.FarmQuest
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class QuestHistoryClickHandler(
    private val questManager: QuestManager
) : ViewClickHandler<PageContext> {
    override fun onClick(
        player: Player, data: PageContext?, item: ItemStack, event: InventoryClickEvent
    ) {
        requireNotNull(data)
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.quests.history") {
            when (it) {
                "return" -> player.openView(QuestsView::class)
                "next" -> player.openView(QuestHistoryView::class, data.next())
                "previous" -> player.openView(QuestHistoryView::class, data.previous())
                "start" -> {
                    val currentQuest = questManager.currentQuest(player.uniqueId)

                    if (currentQuest != null) {
                        player.closeInventory()
                        player.respond("missao.fazendo")
                        return@useReferenceIfPresent
                    }

                    val master = questManager.master(player.uniqueId)
                    val historyQuest = questManager.history().getQuest(
                        master.questHistoryProgress()
                    )
                    val quest = FarmQuest(
                        historyQuest.id,
                        historyQuest.quest.type()
                    )
                    master.startQuest(quest)
                    player.respond("missao_historia.iniciada")
                    player.openView(QuestsView::class)
                }
            }
        }
    }
}