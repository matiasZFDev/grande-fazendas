package com.grandemc.fazendas.bukkit.view.hoe

import com.grandemc.fazendas.config.FarmHoeConfig
import com.grandemc.fazendas.global.dottedFormat
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.storage.player.model.CustomEnchant
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.intFormat
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class HoeProcessor(
    private val playerManager: PlayerManager,
    private val farmHoeConfig: FarmHoeConfig
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        return newItemProcessing(items) {
            CustomEnchant.values().forEach { enchant ->
                val farmHoe = playerManager.player(player.uniqueId).hoe()
                val enchantLevel = farmHoe.enchantLevel(enchant)
                val enchantLevelData = farmHoeConfig.get()
                    .getEnchant(enchant).levels
                    .level(enchantLevel.toInt())
                modify(enchant.configName()) {
                    it
                        .formatName(
                            "{valor}" to enchantLevelData.upgrades.value.intFormat()
                        )
                        .formatLore(
                            "{nivel}" to enchantLevel.dottedFormat(),
                            "{custo}" to enchantLevelData.requirements.gold.toFormat()
                        )
                }
            }
        }
    }
}