package com.grandemc.fazendas.bukkit.view.craft.select

import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.post.external.lib.cache.config.chunk.base.ItemsChunk
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.bukkit.displayFrom
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.global.bukkit.formatName
import com.grandemc.post.external.lib.global.bukkit.nms.NBTReference
import com.grandemc.post.external.lib.global.bukkit.nms.addNBTValue
import com.grandemc.post.external.lib.global.timeFormat
import com.grandemc.post.external.lib.global.toFormat
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import net.minecraft.server.v1_8_R3.NBTTagByte
import org.bukkit.entity.Player

class CraftSelectProcessor(
    private val industryConfig: IndustryConfig,
    private val itemsConfig: ItemsChunk,
    private val materialsConfig: MaterialsConfig
) : MenuItemsProcessor.Stateless() {
    override fun process(player: Player, items: MenuItems): Collection<SlotItem> {
        val baseItems = items.values()
        val recipeItems = industryConfig.get().recipes()
            .zip(industryConfig.get().selectMenuSlots())
            .map { (recipe, slot) ->
                val bakeTime = industryConfig.get().getById(recipe.id).bakeTime
                val materialConfig = materialsConfig.get().getById(recipe.materialId)
                val item = materialConfig.item
                    .displayFrom(itemsConfig.value("material_craft"))
                    .formatName("{nome}" to materialConfig.name)
                    .formatLore(
                        "{tempo}" to bakeTime.timeFormat(),
                        "{ouro}" to materialConfig.goldPrice.toFormat(),
                    )
                    .addNBTValue(
                        NBTReference.VIEW,
                        "gfazendas.craft.select.recipe",
                        NBTTagByte(recipe.id)
                    )
                SlotItem(slot, item)
            }
        return baseItems + recipeItems
    }
}