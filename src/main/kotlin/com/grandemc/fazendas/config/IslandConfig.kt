package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.config.model.level.LevelContainer
import com.grandemc.fazendas.config.model.level.island.IslandLevel
import com.grandemc.fazendas.config.model.level.island.iterative.IslandLevelRequirements
import com.grandemc.fazendas.config.model.level.island.iterative.IslandLevelUpgrades
import com.grandemc.fazendas.config.model.level.island.iterative.IterativeIslandLevels
import com.grandemc.fazendas.config.model.level.island.iterative.pattern.EveryAfterPattern
import com.grandemc.fazendas.config.model.level.island.iterative.pattern.EveryPattern
import com.grandemc.fazendas.config.model.level.island.iterative.pattern.LevelPattern
import com.grandemc.fazendas.config.model.level.island.iterative.pattern.WhenPattern
import com.grandemc.fazendas.global.asSchematic
import com.grandemc.fazendas.util.Lazy
import com.grandemc.fazendas.util.ViewVector
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.keys
import com.grandemc.post.external.lib.global.bukkit.section
import com.grandemc.post.external.lib.global.bukkit.stringList
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.util.CustomConfig
import com.sk89q.worldedit.BlockVector
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.extent.clipboard.Clipboard
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import java.io.File

class IslandConfig(
    customConfig: CustomConfig,
    private val baseSchematicFile: Lazy<File>
) : StateConfig<IslandConfig.Config>(
    customConfig, GrandeFazendas.CONTEXT
) {
    inner class Config(
        val worldName: String,
        val islandDistance: Int,
        val spawn: Vector,
        val cropBlock: Int,
        val leaveCommand: String,
        val islandNpcs: IslandNpcs,
        val evolution: IslandEvolution,
        val baseSchematic: Clipboard
    )
    inner class IslandNpcs(
        val terrains: IslandNPC,
        val quests: IslandNPC,
        val industry: IslandNPC
    )
    inner class IslandNPC(
        val name: String,
        val position: ViewVector,
        val hologramLines: List<String>
    )
    inner class IslandEvolution(
        val maxLevel: Int,
        val levels: LevelContainer<IslandLevel>
    )

    override fun fetch(config: FileConfiguration): Config {
        return config.run {
            Config(
                getString("mundo"),
                getInt("distanca"),
                BlockVector(
                    getDouble("spawn.x"),
                    getDouble("spawn.y"),
                    getDouble("spawn.z")
                ),
                getInt("farm_bloco_plantacao"),
                getString("comando_saida"),
                IslandNpcs(
                    IslandNPC(
                        getString("npcs.terrenos.nome_skin"),
                        ViewVector(
                            getDouble("npcs.terrenos.posicao.x"),
                            getDouble("npcs.terrenos.posicao.y"),
                            getDouble("npcs.terrenos.posicao.z"),
                            getDouble("npcs.terrenos.posicao.yaw").toFloat(),
                            getDouble("npcs.terrenos.posicao.pitch").toFloat()
                        ),
                        stringList("npcs.terrenos.holograma").color()
                    ),
                    IslandNPC(
                        getString("npcs.missoes.nome_skin"),
                        ViewVector(
                            getDouble("npcs.missoes.posicao.x"),
                            getDouble("npcs.missoes.posicao.y"),
                            getDouble("npcs.missoes.posicao.z"),
                            getDouble("npcs.missoes.posicao.yaw").toFloat(),
                            getDouble("npcs.missoes.posicao.pitch").toFloat()
                        ),
                        stringList("npcs.missoes.holograma").color()
                    ),
                    IslandNPC(
                        getString("npcs.industria.nome_skin"),
                        ViewVector(
                            getDouble("npcs.industria.posicao.x"),
                            getDouble("npcs.industria.posicao.y"),
                            getDouble("npcs.industria.posicao.z"),
                            getDouble("npcs.missoes.posicao.yaw").toFloat(),
                            getDouble("npcs.missoes.posicao.pitch").toFloat()
                        ),
                        stringList("npcs.industria.holograma").color()
                    )
                ),
                IslandEvolution(
                    getInt("evolucao.nivel_maximo"),
                    iterativeLevels(section("evolucao.niveis"))
                ),
                baseSchematicFile.get().asSchematic(getString("mundo"))
            )
        }
    }

    private fun iterativeLevels(section: ConfigurationSection): IterativeIslandLevels {
        val requirements = mutableListOf<IslandLevelRequirements>()
        val upgrades = mutableListOf<IslandLevelUpgrades>()
        section.section("requisitos").keys().forEach { key ->
            patternResolve(key) {
                requirements.add(
                    IslandLevelRequirements(
                    it, section.getInt("requisitos.$key.xp")
                ))
            }

        }
        section.section("melhorias").keys().forEach { key ->
            patternResolve(key) {
                upgrades.add(
                    IslandLevelUpgrades(
                    it,
                    section.getInt("melhorias.$key.missoes_diarias")
                )
                )
            }
        }
        return IterativeIslandLevels(requirements, upgrades)
    }

    private fun patternResolve(key: String, consumer: (LevelPattern) -> Unit) {
        if (key.startsWith("cada")) {
            val level = key.split("_")[1].toInt()
            consumer(EveryPattern(level))
        }

        else if (key.startsWith("em")) {
            val level = key.split("_")[1].toInt()
            consumer(WhenPattern(level))
        }

        else if (key.startsWith("depois")) {
            val patternFormat = key.split("_")
            val afterLevel = patternFormat[1].toInt()
            val everyLevel = patternFormat[3].toInt()
            consumer(EveryAfterPattern(afterLevel, everyLevel))
        }
    }
}