package com.grandemc.fazendas.bukkit.view.island.menu

import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.manager.FarmManager
import com.grandemc.fazendas.manager.IslandManager
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatLoreList
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.global.dottedFormat
import com.grandemc.post.external.lib.global.formatReplace
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class IslandMainMenuProcessor(
    private val islandManager: IslandManager,
    private val farmManager: FarmManager,
    private val landManager: LandManager,
    private val islandConfig: IslandConfig,
    private val farmsConfig: FarmsConfig,
    private val cropsConfig: CropsConfig
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        val landFormats = islandConfig.get().landFormats
        return newItemProcessing(items) {
            val farm = farmManager.farm(player.uniqueId)

            if (islandManager.insideIsland(player.uniqueId)) {
                remove("fazenda_fora")
                modify("fazenda_dentro") {
                    it.formatLore(
                        "{nivel}" to farm.level().toString(),
                        "{xp}" to farm.xp().dottedFormat()
                    )
                }
            }

            else {
                remove("fazenda_dentro")
                modify("fazenda_fora") {
                    it.formatLore(
                        "{nivel}" to farm.level().toString(),
                        "{xp}" to farm.xp().dottedFormat()
                    )
                }
            }

            modify("plantios") {
                it.formatLoreList(
                    "{<plantios>}" to farmsConfig.get().farms.map { farm ->
                        landFormats.land.formatReplace(
                            "{nome}" to farm.config.name.color(),
                            "{estado}" to if (!landManager.hasLand(
                                    player.uniqueId, farm.config.id
                            ))
                                landFormats.blocked
                            else {
                                val land = landManager.land(
                                    player.uniqueId, farm.config.id
                                )

                                if (land.isResetting() && land.cropId() != null)
                                    cropsConfig.get().getCrop(land.cropId()!!).name
                                else
                                    landFormats.inactive
                            }
                        )
                    }
                )
            }
        }
    }
}