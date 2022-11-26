package com.grandemc.fazendas.config.model.crop

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.config.CropsConfig
import com.grandemc.fazendas.config.model.block.PlaceBlocks
import com.grandemc.fazendas.config.model.block.SimpleBlocks
import com.grandemc.fazendas.config.model.block.SkullBlocks
import com.grandemc.fazendas.config.model.crop.process.CropProcess
import com.grandemc.fazendas.config.model.crop.process.FarmCropProcess
import com.grandemc.fazendas.config.model.crop.process.PlainCropProcess
import com.grandemc.fazendas.global.VectorArea
import com.grandemc.post.external.lib.global.bukkit.section
import com.grandemc.post.external.lib.global.bukkit.textureToBase64
import com.sk89q.worldedit.blocks.BaseBlock
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection

enum class CropType(private val configName: String) {
    FARM("farm") {
        override fun fetchProcess(section: ConfigurationSection): CropsConfig.ProcessData {
            return CropsConfig.ProcessData(
                CropType.fromConfigName(section.getString("tipo")),
                SimpleBlocks(BaseBlock(
                    section.getString("plantacao").let(Material::getMaterial).id,
                    0
                )),
                SimpleBlocks(BaseBlock(
                    section.getString("plantacao").let(Material::getMaterial).id,
                    7
                ))
            )
        }

        override fun initializeProcess(cropsArea: VectorArea): CropProcess {
            return FarmCropProcess(cropsArea)
        }
    },
    FARM_BLOCK("farm_bloco") {
        override fun fetchProcess(section: ConfigurationSection): CropsConfig.ProcessData {
            return CropsConfig.ProcessData(
                CropType.fromConfigName(section.getString("tipo")),
                SimpleBlocks(BaseBlock(
                    section.getString("plantacao").let(Material::getMaterial).id,
                    0
                )),
                resolveBlocks(section.section("bloco"))
            )
        }

        override fun initializeProcess(cropsArea: VectorArea): CropProcess {
            return FarmCropProcess(cropsArea)
        }
    },
    FARM_CUSTOM("farm_custom") {
        override fun fetchProcess(section: ConfigurationSection): CropsConfig.ProcessData {
            return CropsConfig.ProcessData(
                CropType.fromConfigName(section.getString("tipo")),
                resolveBlocks(section.section("plantado")),
                resolveBlocks(section.section("crescido"))
            )
        }

        override fun initializeProcess(cropsArea: VectorArea): CropProcess {
            return FarmCropProcess(cropsArea)
        }
    },
    CUSTOM("custom") {
        override fun fetchProcess(section: ConfigurationSection): CropsConfig.ProcessData {
            return CropsConfig.ProcessData(
                CropType.fromConfigName(section.getString("tipo")),
                resolveBlocks(section.section("plantado")),
                resolveBlocks(section.section("crescido"))
            )
        }

        override fun initializeProcess(cropsArea: VectorArea): CropProcess {
            return PlainCropProcess(cropsArea)
        }
    };

    companion object {
        fun fromConfigName(configName: String): CropType {
            return values().first { it.configName == configName }
        }

        private fun resolveBlocks(section: ConfigurationSection): PlaceBlocks {
            return if (section.contains("id"))
                SimpleBlocks(BaseBlock(section.getInt("id"), section.getInt("data")))
            else
                SkullBlocks(section.getString("head").textureToBase64())
        }
    }

    fun configName(): String {
        return configName
    }

    abstract fun fetchProcess(section: ConfigurationSection): CropsConfig.ProcessData
    abstract fun initializeProcess(cropsArea: VectorArea): CropProcess
}