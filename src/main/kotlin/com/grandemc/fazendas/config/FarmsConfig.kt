package com.grandemc.fazendas.config

import com.grandemc.fazendas.global.asSchematic
import com.grandemc.fazendas.global.createIfNotExists
import com.grandemc.post.external.lib.cache.config.Updatable
import com.grandemc.post.external.lib.global.bukkit.*
import com.sk89q.worldedit.BlockVector
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.extent.clipboard.Clipboard
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.FileFilter

class FarmsConfig(
    private val plugin: Plugin,
    private val farmsRootDirPath: String,
    private val farmWorldName: String,
    private val cropBlock: Int
) : Updatable {
    inner class Config(
        val farms: List<Farm>
    )
    inner class Farm(
        val config: FarmConfig,
        val schematics: List<FarmSchematic>
    )
    inner class FarmConfig(
        val id: Byte,
        val baseSchematic: Clipboard,
        val position: Vector,
        val requirements: FarmRequirements,
        val levels: FarmLevels
    )
    inner class FarmRequirements(
        val islandLevel: Byte,
        val items: List<ItemRequirement>,
        val gold: Double
    )
    inner class ItemRequirement(
        val type: String,
        val amount: Short
    )
    inner class FarmLevels(private val levels: List<FarmLevel>) {
        fun hasLevel(level: Byte): Boolean {
            return level >= levels.size
        }

        fun level(level: Byte): FarmLevel {
            return levels[level.toInt()]
        }

        fun nextLevel(level: Byte): FarmLevel? {
            return if (hasLevel(level.inc())) level(level.inc()) else null
        }
    }
    inner class FarmLevel(
        val level: Byte,
        val schematic: String,
        val evolution: FarmLevelEvolution?
    )
    inner class FarmLevelEvolution(
        val xp: Int,
        val gold: Double,
        val nextLevel: Byte
    )
    inner class FarmSchematic(
        val name: String,
        val schematic: Clipboard,
        val cropVectors: List<Vector>
    )

    private lateinit var config: Config

    fun get(): Config = config

    override fun update() {
        val farmsDir = File(plugin.dataFolder, farmsRootDirPath).createIfNotExists()
        config = Config(
            farmsDir.listFiles(FileFilter { it.isDirectory })!!.map { farmDir ->
                val farmYamlConfig = YamlConfiguration.loadConfiguration(
                    farmDir.resolve("config.yml")
                )
                val farmConfig = FarmConfig(
                    farmYamlConfig.getByte("id"),
                    farmDir.resolve("base.schematic").asSchematic(farmWorldName),
                    BlockVector(
                        farmYamlConfig.getInt("posicao.x"),
                        farmYamlConfig.getInt("posicao.y"),
                        farmYamlConfig.getInt("posicao.z")
                    ),
                    FarmRequirements(
                        farmYamlConfig.getByte("requisitos.nivel_ilha"),
                        farmYamlConfig.keys().map {
                            ItemRequirement(
                                it,
                                farmYamlConfig.getShort("requisitos.itens.$it")
                            )
                        },
                        farmYamlConfig.getDouble("requisitos.ouro")
                    ),
                    FarmLevels(
                        farmYamlConfig.section("niveis").mappedSection {
                            FarmLevel(
                                getByte("nivel"),
                                getString("schematic"),
                                if (!isBoolean("evolucao"))
                                    FarmLevelEvolution(
                                        getInt("evolucao.xp"),
                                        getDouble("evolucao.ouro"),
                                        getByte("evolucao.nivel")
                                    )
                                else
                                    null
                            )
                        }
                    )
                )
                val schematics = farmDir.resolve("schematic").listFiles()!!.map {
                    val clipboard = it.asSchematic(farmWorldName)
                    val cropVectors = clipboard.region.toList().filter { vector ->
                        clipboard.getBlock(vector).type == cropBlock
                    }
                    cropVectors.forEach { vector ->
                        clipboard.setBlock(vector, BaseBlock(Material.AIR.id))
                    }
                    FarmSchematic(
                        it.name,
                        clipboard,
                        cropVectors
                    )
                }
                Farm(farmConfig, schematics)
            }
        )
    }
}