package com.grandemc.fazendas.bukkit.view.hoe

import com.grandemc.fazendas.bukkit.view.HoeView
import com.grandemc.fazendas.config.FarmHoeConfig
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.PlayerManager
import com.grandemc.fazendas.storage.player.model.CustomEnchant
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.hasReferenceTag
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class HoeClickHandler(
    private val playerManager: PlayerManager,
    private val goldBank: GoldBank,
    private val farmHoeConfig: FarmHoeConfig
) : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.hoe.enchant") {
            if (!player.itemInHand.hasReferenceTag(NBTReference.ITEM, "gfazendas.hoe")) {
                player.closeInventory()
                player.respond("enxada.upar_mao")
                return@useReferenceIfPresent
            }

            val enchant = CustomEnchant.fromConfigName(it)
            val farmHoe = playerManager.player(player.uniqueId).hoe()
            val enchantConfig = farmHoeConfig.get().getEnchant(enchant)
            val enchantLevel = farmHoe.enchantLevel(enchant).toInt()

            if (enchantLevel == enchantConfig.maxLevel) {
                player.closeInventory()
                player.respond("enxada.upar_maximo")
                return@useReferenceIfPresent
            }

            val enchantLevelData = enchantConfig.levels.level(enchantLevel)

            if (!goldBank.has(player.uniqueId, enchantLevelData.requirements.gold)) {
                player.closeInventory()
                player.respond("enxada.upar_ouro")
                return@useReferenceIfPresent
            }

            farmHoe.upgradeEnchant(enchant)
            goldBank.withdraw(player.uniqueId, enchantLevelData.requirements.gold)
            player.openView(HoeView::class)
            player.respond("enxada.upada")
        }
    }
}