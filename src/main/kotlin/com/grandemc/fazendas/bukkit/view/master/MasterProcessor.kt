package com.grandemc.fazendas.bukkit.view.master

import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class MasterProcessor(
    private val goldBank: GoldBank
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        return newItemProcessing(items) {
            modify("banco") {
                it.formatLore("{ouro}" to goldBank.gold(player.uniqueId).toFormat())
            }
        }
    }
}