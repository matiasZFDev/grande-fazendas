package com.grandemc.fazendas.bukkit.view.upgrades

import com.grandemc.fazendas.manager.UpgradesManager
import com.grandemc.fazendas.storage.player.model.FarmUpgradeType
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.copyNBTs
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatLoreList
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTReference
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class UpgradesProcessor(
    private val upgradesManager: UpgradesManager,
    private val itemsConfig: ItemsChunk
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        return newItemProcessing(items) {
            FarmUpgradeType.values.forEach { upgradeType ->
                if (upgradesManager.maxLevel(player.uniqueId, upgradeType)) {
                    modify(upgradeType.configName()) {
                        itemsConfig
                            .value("melhoria_maximo")
                            .copyNBTs(it)
                            .formatName("{nome}" to it.itemMeta.displayName)
                            .formatLoreList("{<lore>}" to it.itemMeta.lore)
                            .formatLore(
                                "{valor}" to upgradesManager.upgradeConfig(
                                    player.uniqueId, upgradeType
                                ).value.let(upgradeType::format)
                            )
                    }
                }

                else if (upgradesManager.canUpgrade(player.uniqueId, upgradeType)) {
                    val nextUpgrade = upgradesManager.nextUpgradeConfig(
                        player.uniqueId, upgradeType
                    )
                    modify(upgradeType.configName()) {
                        itemsConfig
                            .value("melhoria_upavel")
                            .copyNBTs(it)
                            .formatName("{nome}" to it.itemMeta.displayName)
                            .formatLoreList("{<lore>}" to it.itemMeta.lore)
                            .formatLore(
                                "{valor}" to upgradesManager.upgradeConfig(
                                    player.uniqueId, upgradeType
                                ).value.let(upgradeType::format),
                                "{seguinte}" to nextUpgrade.value.let(upgradeType::format),
                                "{custo}" to nextUpgrade.cost.toFormat()
                            )
                            .addNBTReference(
                                NBTReference.VIEW,
                                "gfazendas.upgrades.upgrade",
                                upgradeType.configName()
                            )
                    }
                }

                else {
                    val nextUpgrade = upgradesManager.nextUpgradeConfig(
                        player.uniqueId, upgradeType
                    )
                    modify(upgradeType.configName()) {
                        itemsConfig
                            .value("melhoria_nao_upavel")
                            .copyNBTs(it)
                            .formatName("{nome}" to it.itemMeta.displayName)
                            .formatLoreList("{<lore>}" to it.itemMeta.lore)
                            .formatLore(
                                "{valor}" to upgradesManager.upgradeConfig(
                                    player.uniqueId, upgradeType
                                ).value.let(upgradeType::format),
                                "{seguinte}" to nextUpgrade.value.let(upgradeType::format),
                                "{custo}" to nextUpgrade.cost.toFormat()
                            )
                    }
                }
            }
        }
    }
}