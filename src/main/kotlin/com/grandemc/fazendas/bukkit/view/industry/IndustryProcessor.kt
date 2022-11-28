package com.grandemc.fazendas.bukkit.view.industry

import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.manager.IndustryManager
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.copyNBTs
import com.grandemc.post.external.lib.global.bukkit.displayFrom
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.newItemProcessing
import com.grandemc.post.external.lib.global.timeFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class IndustryProcessor(
    private val industryManager: IndustryManager,
    private val industryConfig: IndustryConfig,
    private val materialsConfig: MaterialsConfig
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        return newItemProcessing(items) {
            if (!industryManager.isBaking(player.uniqueId)) {
                remove("craft_ativo")
                remove("craft_pronto")
            }

            else {
                val recipe = industryManager.currentRecipe(player.uniqueId)!!
                val recipeConfig = industryConfig.get().getById(recipe.id())
                val materialConfig = materialsConfig.get().getById(recipeConfig.materialId)

                if (recipe.timeLeft() == -1) {
                    remove("craft_ativo")
                    remove("craft_inativo")
                    modify("craft_pronto") {
                        materialConfig.item
                            .displayFrom(it)
                            .copyNBTs(it)
                            .formatLore("{receita}" to materialConfig.name)
                    }
                }

                else {
                    remove("craft_inativo")
                    remove("craft_pronto")
                    modify("craft_ativo") {
                        it.formatLore(
                            "{receita}" to materialConfig.name,
                            "{tempo}" to recipe.timeLeft().timeFormat()
                        )
                    }
                }
            }
        }
    }
}