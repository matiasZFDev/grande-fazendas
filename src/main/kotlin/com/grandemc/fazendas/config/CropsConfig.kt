package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.config.model.block.PlaceBlocks
import com.grandemc.fazendas.config.model.crop.CropType
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.*
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.util.CustomConfig
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.inventory.ItemStack

class CropsConfig(customConfig: CustomConfig) : StateConfig<CropsConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        val menuSlots: List<Int>,
        private val cropList: List<Crop>
    ) {
        fun crops(): List<Crop> {
            return cropList
        }

        fun getCrop(cropId: Byte): Crop {
            return cropList.first { it.id == cropId }
        }

        fun getCropByNameId(nameId: String): Crop {
            return cropList.first { it.nameId == nameId }
        }
    }
    inner class Crop(
        val id: Byte,
        val nameId: String,
        val process: ProcessData,
        val item: ItemStack,
        val name: String,
        val islandLevel: Byte,
        val materialId: Byte,
        val xp: Int,
        val plantXp: Int,
        val reset: Int,
    )
    class ProcessData(
        val type: CropType,
        val startBlocks: PlaceBlocks,
        val grownBlocks: PlaceBlocks
    )

    override fun fetch(config: FileConfiguration): Config {
        return config.section("plantacoes").mappedKeys().map { (key, section) ->
            Crop(
                section.getByte("id"),
                key,
                CropType
                    .fromConfigName(section.getString("processo.tipo"))
                    .fetchProcess(section.section("processo")),
                section.buildItem("iten", false),
                section.getString("nome").color(),
                section.getByte("nivel_ilha"),
                section.getByte("material_id"),
                section.getInt("xp"),
                section.getInt("xp_plantar"),
                section.getInt("reset") * 60
            )
        }.let { Config(config.intList("menu_slots"), it) }
    }
}