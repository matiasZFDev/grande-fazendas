package com.grandemc.fazendas.bukkit.view.island

import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.global.intFormat
import com.grandemc.fazendas.global.timeFormat
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class IslandProcessor(
    private val playerManager: PlayerManager,
    private val farmManager: FarmManager,
    private val islandConfig: IslandConfig
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        val booster = playerManager.player(player.uniqueId).booster()
        return newItemProcessing(items) {
            if (booster == null)
                remove("booster_ativo")
            else {
                remove("booster_inativo")
                modify("booster_ativo") {
                    it.formatLore(
                        "{boost}" to booster.boost().intFormat(),
                        "{duracao}" to booster.timeLeft().timeFormat()
                    )
                }
            }

            val island = farmManager.farm(player.uniqueId)
            val islandLevel = island.level()
            val currentLevel = islandConfig.get().evolution.levels.level(
                islandLevel.toInt()
            )

            if (islandConfig.get().evolution.maxLevel == islandLevel.toInt()) {
                remove("ilha_upavel")
                remove("ilha_nao_upavel")
                modify("ilha_maximo") {
                    it.formatLore(
                        "{nivel}" to islandLevel.toString(),
                        "{xp}" to island.xp().commaFormat(),
                        "{diarias}" to currentLevel.upgrades.dailyQuests.toString()
                    )
                }
            }

            else {
                val nextLevel = islandConfig.get().evolution.levels.level(
                    islandLevel.toInt().inc()
                )

                if (island.xp() >= currentLevel.requirements.xp) {
                    remove("ilha_nao_upavel")
                    remove("ilha_maximo")
                    modify("ilha_upavel") {
                        it.formatLore(
                            "{nivel}" to islandLevel.toString(),
                            "{xp}" to island.xp().commaFormat(),
                            "{xp_preciso}" to currentLevel.requirements.xp.commaFormat(),
                            "{diarias}" to currentLevel.upgrades.dailyQuests.toString(),
                            "{diarias_evolucao}" to nextLevel.upgrades.dailyQuests.toString()
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
                            "{xp_preciso}" to currentLevel.requirements.xp.commaFormat(),
                            "{diarias}" to currentLevel.upgrades.dailyQuests.toString(),
                            "{diarias_evolucao}" to nextLevel.upgrades.dailyQuests.toString()
                        )
                    }
                }
            }
        }
    }
}