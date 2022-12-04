package com.grandemc.fazendas.bukkit.view.island.top

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.init.model.IslandTopState
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.changeTextureIfHead
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.offlinePlayer
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class IslandTopProcessor(
    private val topState: IslandTopState,
    private val itemsConfig: ItemsChunk
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        val baseItems = items.values()
        val topItems = topState.get()
            .zip(GrandeFazendas.DESC_MID_SLOTS_PATTERN)
            .mapIndexed { index, (pos, slot) ->
                val item = itemsConfig
                    .value("ilha_top")
                    .changeTextureIfHead(pos.playerId)
                    .formatName(
                        "{lugar}" to index.inc().toString(),
                        "{jogador}" to pos.playerId.offlinePlayer().name
                    )
                    .formatLore(
                        "{nivel}" to pos.level.toString(),
                        "{xp}" to pos.xp.commaFormat(),
                        "{missoes}" to pos.questsDone.commaFormat()
                    )
                SlotItem(slot, item)
            }
        return baseItems + topItems
    }
}