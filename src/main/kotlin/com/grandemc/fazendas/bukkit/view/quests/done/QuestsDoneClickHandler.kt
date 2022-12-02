package com.grandemc.fazendas.bukkit.view.quests.done

import com.grandemc.fazendas.bukkit.view.PageContext
import com.grandemc.fazendas.bukkit.view.QuestsDoneView
import com.grandemc.fazendas.bukkit.view.QuestsView
import com.grandemc.fazendas.global.openView
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class QuestsDoneClickHandler : ViewClickHandler<PageContext> {
    override fun onClick(
        player: Player, data: PageContext?, item: ItemStack, event: InventoryClickEvent
    ) {
        requireNotNull(data)
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.quests.done") {
            when (it) {
                "return" -> player.openView(QuestsView::class)
                "next" -> player.openView(QuestsDoneView::class, data.next())
                "previous" -> player.openView(QuestsDoneView::class, data.previous())
            }
        }
    }
}