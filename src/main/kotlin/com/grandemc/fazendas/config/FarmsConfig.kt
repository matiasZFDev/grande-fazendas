package com.grandemc.fazendas.config

import com.boydti.fawe.`object`.IntegerTrio
import com.boydti.fawe.`object`.clipboard.DiskOptimizedClipboard
import com.grandemc.fazendas.global.*
import com.grandemc.post.external.lib.cache.config.Updatable
import com.grandemc.post.external.lib.global.bukkit.*
import com.sk89q.jnbt.CompoundTag
import com.sk89q.worldedit.BlockVector
import com.sk89q.worldedit.CuboidClipboard
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.blocks.BaseBlock
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.schematic.SchematicFormat
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.FileFilter
import java.lang.reflect.Field
import java.util.LinkedList

class FarmsConfig(
    private val plugin: Plugin,
    private val farmsRootDirPath: String,
    private val islandConfig: IslandConfig
) : Updatable {
    inner class Config(
        val farms: List<Farm>
    ) {
        fun getFarmById(farmId: Byte): Farm {
            return farms.first { it.config.id == farmId }
        }
    }

    inner class Farm(
        val config: FarmConfig,
        val schematics: List<FarmSchematic>
    ) {
        fun getSchematicByName(name: String): FarmSchematic {
            return schematics.first { name == it.name }
        }
    }
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
            return levels[level.dec().toInt()]
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
        val cropVectors: VectorArea
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
                    farmDir.resolve("base.schematic").asSchematic(
                        islandConfig.get().worldName
                    ),
                    BlockVector(
                        farmYamlConfig.getInt("posicao.x"),
                        farmYamlConfig.getInt("posicao.y"),
                        farmYamlConfig.getInt("posicao.z")
                    ),
                    FarmRequirements(
                        farmYamlConfig.getByte("requisitos.nivel_ilha"),
                        farmYamlConfig.section("requisitos.itens").keys().map {
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
                    val clipboard = it.asSchematic(islandConfig.get().worldName)
                    val vectorIt = clipboard.region.iterator()
                    val cropVectors = mutableListOf<Vector>()
                    while (vectorIt.hasNext())
                        vectorIt.next().let { vector ->
                            if (clipboard.getBlock(vector).type == islandConfig.get().cropBlock) {
                                cropVectors.add(vector.toBlockPoint())
                                clipboard.setBlock(vector, BaseBlock(0))
                            }
                        }
                    FarmSchematic(
                        it.nameWithoutExtension,
                        clipboard,
                        VectorArea(cropVectors)
                    )
                }
                Farm(farmConfig, schematics)
            }
        )
    }
}