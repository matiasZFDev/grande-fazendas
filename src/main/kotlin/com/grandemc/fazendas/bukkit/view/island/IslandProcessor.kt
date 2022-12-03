package com.grandemc.fazendas.bukkit.view.island

import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class IslandProcessor(
    private val farmManager: FarmManager,
    private val islandConfig: IslandConfig
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        return newItemProcessing(items) {
            val island = farmManager.farm(player.uniqueId)
            val islandLevel = island.level()

            if (islandConfig.get().evolution.maxLevel == islandLevel.toInt()) {
                remove("ilha_upavel")
                remove("ilha_nao_upavel")
                modify("ilha_maximo") {
                    it.formatLore(
                        "{nivel}" to islandLevel.toString(),
                        "{xp}" to island.xp().commaFormat()
                    )
                }
            }

            else {
                val upgradeXp = islandConfig.get().evolution.levels.level(
                    islandLevel.toInt()
                ).requirements.xp

                if (island.xp() >= upgradeXp) {
                    remove("ilha_nao_upavel")
                    remove("ilha_maximo")
                    modify("ilha_upavel") {
                        it.formatLore(
                            "{nivel}" to islandLevel.toString(),
                            "{xp}" to island.xp().commaFormat(),
                            "{xp_preciso}" to upgradeXp.commaFormat()
                        )
                    }
                }

                else {
                    remove("ilha_upavel")
                    remove("ilha_maximo")
                    modify("ilha_nao_upavel") {
                        it.formatLore(
                            "{nivel}" to islandLevel.toString(),
                            "{xp}" to island.xp().commaFormat(),
                            "{xp_preciso}" to upgradeXp.commaFormat()
                        )
                    }
                }
            }
        }
    }
}