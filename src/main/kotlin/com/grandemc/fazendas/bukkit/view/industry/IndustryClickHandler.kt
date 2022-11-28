package com.grandemc.fazendas.bukkit.view.industry

import com.grandemc.fazendas.bukkit.view.CraftSelectView
import com.grandemc.fazendas.bukkit.view.IndustryView
import com.grandemc.fazendas.bukkit.view.StorageView
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

class IndustryClickHandler(
    private val industryManager: IndustryManager,
    private val industryConfig: IndustryConfig,
    private val storageManager: StorageManager
) : ViewClickHandler.Stateless() {
    override fun onClick(player: Player, item: ItemStack, event: InventoryClickEvent) {
        item.useReferenceIfPresent(NBTReference.VIEW, "gfazendas.industry") {
            when (it) {
                "storage" -> player.openView(StorageView::class)
                "craft" -> player.openView(CraftSelectView::class)
                "collect" -> {
                    val recipe = industryManager.collectRecipe(player.uniqueId)
                    val recipeMaterial = industryConfig.get().getById(
                        recipe.id()
                    ).materialId
                    storageManager.deposit(player.uniqueId, recipeMaterial, 1)
                    player.openView(IndustryView::class)
                    player.respond("receita.coletada")
                }
            }
        }
    }
}