package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.*
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.file.FileConfiguration

class IndustryConfig(customConfig: CustomConfig) : StateConfig<IndustryConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        private val selectMenuSlots: List<Int>,
        private val ingredientFormat: String,
        private val recipes: List<IndustryRecipe>
    ) {
        fun getById(id: Byte): IndustryRecipe {
            return recipes.first { it.id == id }
        }

        fun recipes(): List<IndustryRecipe> {
            return recipes
        }

        fun selectMenuSlots(): List<Int> = selectMenuSlots

        fun ingredientFormat(): String = ingredientFormat
    }
    inner class IndustryRecipe(
        val id: Byte,
        val materialId: Byte,
        val islandLevel: Byte,
        val items: List<ItemRequirement>,
        val bakeTime: Int
    )
    inner class ItemRequirement(
        val name: String,
        val amount: Short
    )

    override fun fetch(config: FileConfiguration): Config {
        return config.section("receitas").mappedSection {
            IndustryRecipe(
                getByte("id"),
                getByte("material_id"),
                getByte("nivel_ilha"),
                section("itens_precisos").keys().map {
                    ItemRequirement(it, getShort("itens_precisos.$it"))
                },
                getInt("tempo_preparacao")
            )
        }.let {
            Config(
                config.intList("selecao_menu_slots"),
                config.getString("formato_ingrediente").color(),
                it
            )
        }
    }
}