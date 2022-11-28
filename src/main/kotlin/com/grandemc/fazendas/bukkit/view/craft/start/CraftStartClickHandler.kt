package com.grandemc.fazendas.bukkit.view.craft.start

import com.grandemc.fazendas.bukkit.view.CraftSelectView
import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.global.openView
import com.grandemc.fazendas.global.respond
import com.grandemc.fazendas.manager.IndustryManager
import com.grandemc.fazendas.manager.StorageManager
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.useReferenceIfPresent
import com.grandemc.post.external.lib.view.pack.ViewClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class CraftStartClickHandler(
    private val industryConfig: IndustryConfig,
    private val storageManager: StorageManager,
    private val industryManager: IndustryManager
) : ViewClickHandler<CraftContext> {
    override fun onClick(player: Player, data: CraftContext?, item: ItemStack, event: InventoryClickEvent) {
        requireNotNull(data)
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.craft.start") {
            when (it) {
                "return" -> player.openView(CraftSelectView::class)
                "start" -> {
                    if (!industryManager.canCraft(player.uniqueId, data.recipeId)) {
                        player.respond("global.error")
                        player.closeInventory()
                        return@useReferenceIfPresent
                    }

                    val recipe = industryConfig.get().getById(data.recipeId)
                    val recipeName = storageManager.materialData(recipe.id).name
                    recipe.items.forEach { requiredItem ->
                        val materialId = storageManager.materialId(
                            requiredItem.name
                        )
                        storageManager.withdraw(
                            player.uniqueId, materialId, requiredItem.amount
                        )
                    }
                    industryManager.startRecipe(player.uniqueId, recipe)
                    player.closeInventory()
                    player.respond("receita.iniciar") {
                        replace("{receita}" to recipeName)
                    }
                }
            }
        }
    }
}