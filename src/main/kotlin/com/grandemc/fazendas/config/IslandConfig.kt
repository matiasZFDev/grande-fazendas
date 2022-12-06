package com.grandemc.fazendas.config

import com.grandemc.fazendas.GrandeFazendas
import com.grandemc.fazendas.config.model.level.LevelContainer
import com.grandemc.fazendas.config.model.level.island.*
import com.grandemc.fazendas.global.asSchematic
import com.grandemc.fazendas.util.Lazy
import com.grandemc.fazendas.util.ViewVector
import com.grandemc.post.external.lib.cache.config.StateConfig
import com.grandemc.post.external.lib.global.bukkit.section
import com.grandemc.post.external.lib.global.bukkit.stringList
import com.grandemc.post.external.lib.global.color
import com.grandemc.post.external.lib.util.CustomConfig
import com.grandemc.post.external.util.ProgressFormat
import com.grandemc.post.external.util.ProgressFormatFetcher
import com.sk89q.worldedit.BlockVector
import com.sk89q.worldedit.Vector
import com.sk89q.worldedit.extent.clipboard.Clipboard
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
        val landHolograms: LandHolograms,
        val evolution: IslandEvolution,
        val baseSchematic: Clipboard,
        val landFormats: LandsFormat
    )
    inner class IslandNpcs(
        val terrains: IslandNPC,
        val master: IslandNPC,
        val industry: IslandNPC
    )
    inner class IslandNPC(
        val name: String,
        val position: ViewVector,
        val hologramLines: List<String>
    )
    inner class LandHolograms(
        val progressFormat: ProgressFormat,
        val noPlantation: String,
        val blockedHologram: List<String>,
        val runningHologram: List<String>,
        val maxedHologram: List<String>
    )
    inner class IslandEvolution(
        val maxLevel: Int,
        val xpMaxValue: String,
        val levels: LevelContainer<IslandLevel>
    )
    inner class LandsFormat(
        val blocked: String,
        val inactive: String
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
                        getString("npcs.mestre.nome_skin"),
                        ViewVector(
                            getDouble("npcs.mestre.posicao.x"),
                            getDouble("npcs.mestre.posicao.y"),
                            getDouble("npcs.mestre.posicao.z"),
                            getDouble("npcs.mestre.posicao.yaw").toFloat(),
                            getDouble("npcs.mestre.posicao.pitch").toFloat()
                        ),
                        stringList("npcs.mestre.holograma").color()
                    ),
                    IslandNPC(
                        getString("npcs.industria.nome_skin"),
                        ViewVector(
                            getDouble("npcs.industria.posicao.x"),
                            getDouble("npcs.industria.posicao.y"),
                            getDouble("npcs.industria.posicao.z"),
                            getDouble("npcs.industria.posicao.yaw").toFloat(),
                            getDouble("npcs.industria.posicao.pitch").toFloat()
                        ),
                        stringList("npcs.industria.holograma").color()
                    )
                ),
                LandHolograms(
                    ProgressFormatFetcher.fromSection(
                        section("hologramas.progresso_formato")
                    ),
                    getString("hologramas.plantacao_inativa").color(),
                    stringList("hologramas.lista.plantio_bloqueado"),
                    stringList("hologramas.lista.plantio"),
                    stringList("hologramas.lista.plantio_maximo")
                ),
                IslandEvolution(
                    getInt("evolucao.nivel_maximo"),
                    getString("evolucao.nivel_maximo_xp"),
                    IslandLevelFetcher(section("evolucao.niveis")).fetch()
                ),
                baseSchematicFile.get().asSchematic(getString("mundo")),
                LandsFormat(
                    config.getString("plantio_formatos.bloqueado").color(),
                    config.getString("plantio_formatos.inativo").color()
                )
            )
        }
    }
}