package com.grandemc.fazendas.bukkit.view.sell

import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.copyNBTs
import com.grandemc.post.external.lib.global.bukkit.displayFrom
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class MaterialSellProcessor(
    private val storageManager: StorageManager
) : MenuItemsProcessor<MaterialSellContext> {
    override fun process(
        player: Player, data: MaterialSellContext?, items: MenuItems
    ): Collection<SlotItem> {
        requireNotNull(data)
        val materialConfig = storageManager.materialData(data.materialId)
        val amount = storageManager.material(player.uniqueId, data.materialId)
        return newItemProcessing(items) {
            modify("material") {
                materialConfig.item
                    .displayFrom(it)
                    .copyNBTs(it)
                    .formatName("{nome}" to materialConfig.name)
                    .formatLore("{preco}" to materialConfig.goldPrice.toFormat())
            }

            modify("quantia") {
                it.formatLore(
                    "{possui}" to amount.commaFormat(),
                    "{quantia}" to data.amount.commaFormat()
                )
            }

            if (amount >= data.amount) {
                remove("venda_nao_possivel")
                modify("venda_possivel") {
                    it.formatLore(
                        "{iten}" to materialConfig.name,
                        "{quantia}" to data.amount.commaFormat(),
                        "{preco}" to materialConfig.goldPrice.times(
                            data.amount
                        ).toFormat()
                    )
                }
            }

            else
                remove("venda_possivel")
        }
    }
}