package com.grandemc.fazendas.bukkit.view.upgrades

import com.grandemc.cash.GrandeCash
import com.grandemc.fazendas.bukkit.view.MasterView
import com.grandemc.fazendas.bukkit.view.UpgradesView
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.UpgradesManager
import com.grandemc.fazendas.storage.player.model.FarmUpgradeType
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class UpgradesClickHandler(
    private val upgradesManager: UpgradesManager
) : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.upgrades") {
            when (it) {
                "return" -> player.openView(MasterView::class)
            }
        }

        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.upgrades.upgrade") {
            val upgradeType = FarmUpgradeType.fromConfigName(it)
            val nextUpgrade = upgradesManager.nextUpgradeConfig(
                player.uniqueId, upgradeType
            )
            GrandeCash.api().withdraw(player.uniqueId, nextUpgrade.cost)
            upgradesManager.upgrade(player.uniqueId, upgradeType)
            player.openView(UpgradesView::class)
            player.respond("melhorias.upada")
        }
    }
}