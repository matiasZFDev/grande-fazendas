package com.grandemc.fazendas.bukkit.listener

import com.grandemc.fazendas.config.*
import com.grandemc.fazendas.global.findWorld
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.global.toWeVector
import com.grandemc.fazendas.manager.*
import com.grandemc.fazendas.storage.player.model.CustomEnchant
import com.grandemc.post.external.lib.global.bukkit.giveItem
import com.grandemc.post.external.lib.global.bukkit.isLeftClick
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.hasReferenceTag
import com.grandemc.post.external.util.random.RandomUtils
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class CropCollectListener(
    private val islandConfig: IslandConfig,
    private val islandManager: IslandManager,
    private val landManager: LandManager,
    private val cropsConfig: CropsConfig,
    private val storageManager: StorageManager,
    private val farmItemManager: FarmItemManager,
    private val playerManager: PlayerManager,
    private val farmHoeConfig: FarmHoeConfig,
    private val lootBoxConfig: LootBoxConfig
) : Listener {
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (!event.isLeftClick() || !event.hasItem() || !event.hasBlock())
            return

        if (event.player.world != islandConfig.get().worldName.findWorld())
            return

        if (!event.item.hasReferenceTag(NBTReference.ITEM, "gfazendas.farm_tool"))
            return

        val islandOrigin = islandManager.islandOrigin(event.player.uniqueId, false)
        val possibleCropVector = event.clickedBlock.location.toWeVector().subtract(
            islandOrigin.blockX,
            0,
            islandOrigin.blockZ
        )
        landManager
            .playerLands(event.player.uniqueId)
            .find {
                it.resetCountdown() < 0 && landManager
                    .landSchematic(event.player.uniqueId, it.level())
                    .cropVectors.mapped().contains(possibleCropVector)
            }
            ?.let { land ->
                event.isCancelled = true

                val landCrop = land.cropId()!!
                val cropData = cropsConfig.get().getCrop(landCrop).let {
                    if (it == null)
                        return
                    it
                }
                val farmHoe = playerManager.player(event.player.uniqueId).hoe()
                val xpUpgrade = farmHoeConfig.get()
                    .getEnchant(CustomEnchant.EXPERIENT).levels
                    .level(farmHoe.enchantLevel(CustomEnchant.EXPERIENT).toInt())
                val replantUpgrade = farmHoeConfig.get()
                    .getEnchant(CustomEnchant.REPLANT).levels
                    .level(farmHoe.enchantLevel(CustomEnchant.REPLANT).toInt())
                val radarUpgrade = farmHoeConfig.get()
                    .getEnchant(CustomEnchant.RADAR).levels
                    .level(farmHoe.enchantLevel(CustomEnchant.RADAR).toInt())

                if (!RandomUtils.roll(replantUpgrade.upgrades.value))
                    event.clickedBlock.type = Material.AIR
                else
                    event.player.respond("plantio.replantado")

                if (RandomUtils.roll(radarUpgrade.upgrades.value)) {
                    lootBoxConfig.get()
                        .getByLandId(land.typeId())
                        .takeIf(List<LootBoxConfig.LootBox>::isNotEmpty)
                        ?.let { lootBoxes ->
                            val roll = (Math.random() * lootBoxes.size).toInt()
                            val lootBox = lootBoxes[roll]
                            val lootBoxItem = farmItemManager.createLootBox(lootBox)
                            event.player.giveItem(lootBoxItem)
                            event.player.respond("plantio.lootbox") {
                                replace("{lootbox}" to lootBox.name)
                            }
                        }
                }

                playerManager.player(event.player.uniqueId).hoe().incrementCollectCount()
                farmItemManager.updateFarmToolName(
                    event.player.uniqueId,
                    event.player.itemInHand
                )
                val cropXp = (cropData.xp * xpUpgrade.upgrades.value).toInt()
                storageManager.deposit(event.player.uniqueId, landCrop, 1)
                land.addXp(cropXp)
                event.player.respond("plantio.coletada") {
                    replace(
                        "{plantacao}" to cropData.name,
                        "{xp}" to cropXp.toString()
                    )
                }
            }
    }
}