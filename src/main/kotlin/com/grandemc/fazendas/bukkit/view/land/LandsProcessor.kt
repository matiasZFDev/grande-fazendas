package com.grandemc.fazendas.bukkit.view.land

import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.LandsConfig
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.global.toFormat
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatLoreList
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTReference
import com.grandemc.post.external.lib.global.formatReplace
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class LandsProcessor(
    private val landsConfig: LandsConfig,
    private val farmsConfig: FarmsConfig,
    private val landManager: LandManager,
    private val goldBank: GoldBank,
    private val storageManager: StorageManager,
    private val itemsConfig: ItemsChunk
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        val playerId = player.uniqueId
        val baseItems = items.values()
        return baseItems + farmsConfig.get().farms
            .sortedBy { it.config.id }
            .map { it.config }
            .zip(landsConfig.get().menuSlots)
            .map { (farm, slot) ->
                val item = if (!landManager.hasLand(playerId, farm.id)) {
                    val canPurchase =
                        goldBank.has(playerId, farm.requirements.gold) &&
                        farm.requirements.items.all {
                            val id = storageManager.materialId(it.type)
                            storageManager.has(
                                playerId, id, it.amount
                            )
                        }
                    if (!canPurchase)
                        itemsConfig
                            .value("plantio_nao_compravel")
                            .formatLore("{ouro}" to farm.requirements.gold.toFormat())
                            .formatLoreList(
                                "{<itens>}" to farm.requirements.items.map { item ->
                                    landsConfig.get().requiredItemFormat.formatReplace(
                                        "{quantia}" to item.amount.toFormat(),
                                        "{nome}" to storageManager.materialData(
                                            item.type
                                        ).name
                                    )
                                }
                            )
                    else
                        itemsConfig
                            .value("plantio_compravel")
                            .formatLore("{ouro}" to farm.requirements.gold.toFormat())
                            .formatLoreList(
                                "{<itens>}" to farm.requirements.items.map { item ->
                                    landsConfig.get().requiredItemFormat.formatReplace(
                                        "{quantia}" to item.amount.toFormat(),
                                        "{nome}" to storageManager.materialData(
                                            item.type
                                        ).name
                                    )
                                }
                            )
                            .addNBTReference(
                                NBTReference.VIEW, "gfazendas.lands", "purchasable"
                            )
                }
                else {
                    val land = landManager.land(playerId, farm.id)
                    itemsConfig
                        .value("plantio")
                        .formatLore(
                            "{nivel}" to land.level().toString(),
                            "{xp}" to land.xp().commaFormat(),
                            "{plantacao}" to (land.cropId()?.let { farmCropId ->
                                storageManager.materialData(farmCropId).name
                            } ?: landsConfig.get().notSelectedCrop)
                        )
                        .addNBTReference(
                            NBTReference.VIEW, "gfazendas.lands", "info"
                        )
                }

                SlotItem(slot, item)
            }
    }
}