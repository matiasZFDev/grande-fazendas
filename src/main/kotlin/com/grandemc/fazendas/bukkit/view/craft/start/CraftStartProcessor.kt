package com.grandemc.fazendas.bukkit.view.craft.start

import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.global.commaFormat
import com.grandemc.fazendas.manager.IndustryManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.displayFrom
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatLoreList
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.formatReplace
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.global.timeFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class CraftStartProcessor(
    private val storageManager: StorageManager,
    private val industryConfig: IndustryConfig,
    private val industryManager: IndustryManager
) : MenuItemsProcessor<CraftContext> {
    override fun process(player: Player, data: CraftContext?, items: MenuItems): Collection<SlotItem> {
        requireNotNull(data)
        return newItemProcessing(items) {
            val recipeConfig = industryConfig.get().getById(data.recipeId)
            val materialConfig = storageManager.materialData(recipeConfig.materialId)

            modify("receita") {
                materialConfig.item
                    .displayFrom(it)
                    .formatName("{nome}" to materialConfig.name)
            }

            modify("info") {
                it
                    .formatLore(
                        "{tempo}" to recipeConfig.bakeTime.timeFormat(),
                        "{nivel}" to recipeConfig.islandLevel.toString()
                    )
                    .formatLoreList(
                        "{<itens>}" to recipeConfig.items.map { item ->
                            val ingredient = storageManager.materialData(item.name)
                            industryConfig.get().ingredientFormat()
                                .formatReplace(
                                    "{ingrediente}" to ingredient.name,
                                    "{quantia}" to storageManager.material(
                                        player.uniqueId, ingredient.id
                                    ).commaFormat(),
                                    "{total}" to item.amount.commaFormat(),
                                )
                        }
                    )
            }

            if (industryManager.canCraft(player.uniqueId, recipeConfig))
                remove("iniciar_nao_possivel")
            else
                remove("iniciar_possivel")
        }
    }
}