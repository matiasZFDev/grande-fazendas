package com.grandemc.fazendas.bukkit.view.land

import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.fazendas.config.IslandConfig
import com.grandemc.fazendas.config.MaterialsConfig
import com.grandemc.fazendas.global.findWorld
import com.grandemc.fazendas.global.toLocation
import com.grandemc.fazendas.manager.GoldBank
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.post.external.lib.cache.config.model.SlotItem
import com.grandemc.post.external.lib.cache.config.model.menu.MenuItems
import com.grandemc.post.external.lib.global.*
import com.grandemc.post.external.lib.global.bukkit.formatLore
import com.grandemc.post.external.lib.view.pack.MenuItemsProcessor
import org.bukkit.entity.Player

class LandProcessor(
    private val landManager: LandManager,
    private val goldBank: GoldBank,
    private val farmsConfig: FarmsConfig,
    private val fertilizingConfig: FertilizingConfig,
    private val materialsConfig: MaterialsConfig,
    private val islandConfig: IslandConfig
) : MenuItemsProcessor<LandContext> {
    override fun process(player: Player, data: LandContext?, items: MenuItems): Collection<SlotItem> {
        requireNotNull(data)
        val landData = landManager.land(player.uniqueId, data.landId)
        val landLevel = landData.level()
        val farmData = farmsConfig.get().getFarmById(data.landId)
        val currentLand = farmData.config.levels.level(landLevel)
        val currentLandSchematicData = farmData.getSchematicByName(currentLand.schematic)
        val nextLandLevel = farmsConfig.get().getFarmById(
            data.landId
        ).config.levels.nextLevel(landLevel)
        return newItemProcessing(items) {
            if (nextLandLevel == null) {
                val crops = currentLandSchematicData.cropVectors.vectors().size
                remove("evolucao_upavel")
                remove("evolucao_nao_upavel")
                modify("evolucao_maximo") {
                    it.formatLore(
                        "{nivel}" to landLevel.toString(),
                        "{plantacoes}" to crops.toString()
                    )
                }
            }

            else {
                val canLevelUpLand = currentLand.evolution!!.run {
                    goldBank.has(player.uniqueId, gold) &&
                    landData.xp() >= xp
                }
                val nextLevelSchematicData = farmData.getSchematicByName(nextLandLevel.schematic)

                if (canLevelUpLand) {
                    val currentCrops = currentLandSchematicData.cropVectors.vectors().size
                    val newCrops = nextLevelSchematicData.cropVectors.vectors().size
                    remove("evolucao_nao_upavel")
                    remove("evolucao_maximo")
                    modify("evolucao_upavel") {
                        it.formatLore(
                            "{nivel}" to landLevel.toString(),
                            "{novo_nivel}" to landLevel.inc().toString(),
                            "{plantacoes}" to currentCrops.toString(),
                            "{novas_plantacoes}" to newCrops.toString(),
                            "{xp}" to landData.xp().dottedFormat(),
                            "{xp_precisa}" to currentLand.evolution.xp.dottedFormat(),
                            "{ouro}" to goldBank.gold(player.uniqueId).toFormat(),
                            "{ouro_preciso}" to currentLand.evolution.gold.toFormat()
                        )
                    }
                }

                else {
                    val currentCrops = currentLandSchematicData.cropVectors.vectors().size
                    val newCrops = nextLevelSchematicData.cropVectors.vectors().size
                    remove("evolucao_upavel")
                    remove("evolucao_maximo")
                    modify("evolucao_nao_upavel") {
                        it.formatLore(
                            "{nivel}" to landLevel.toString(),
                            "{novo_nivel}" to landLevel.inc().toString(),
                            "{plantacoes}" to currentCrops.toString(),
                            "{novas_plantacoes}" to newCrops.toString(),
                            "{xp}" to landData.xp().dottedFormat(),
                            "{xp_precisa}" to currentLand.evolution.xp.dottedFormat(),
                            "{ouro}" to goldBank.gold(player.uniqueId).toFormat(),
                            "{ouro_preciso}" to currentLand.evolution.gold.toFormat()
                        )
                    }
                }
            }

            if (landData.boostId() != null) {
                val boostId = landData.boostId()!!
                val fertilizing = fertilizingConfig.get().getById(boostId) ?: throw Error(
                    "O fertilizaante #$boostId não existe!"
                )
                remove("fertilizante_inativo")
                modify("fertilizante_ativo") {
                    it.formatLore(
                        "{nome}" to fertilizing.name,
                        "{reducao}" to fertilizing.boost.intFormat()
                    )
                }
            }

            else {
                remove("fertilizante_ativo")
            }

            if (landData.cropId() != null && landData.resetCountdown() >= 0) {
                val cropName = materialsConfig.get().getById(landData.cropId()!!).name
                remove("plantar_bloqueado")
                remove("plantar_disponivel")
                modify("plantar_gerando") {
                    it.formatLore(
                        "{plantacao}" to cropName,
                        "{geracao}" to landData.resetCountdown().timeFormat(),
                        "{plantacoes}" to currentLandSchematicData
                            .cropVectors
                            .vectors().size.toString()
                    )
                }
            }

            else {
                val cropName = materialsConfig.get().getById(landData.cropId()!!).name
                val world = islandConfig.get().worldName.findWorld()
                val landCrops = landManager.landCrops(player.uniqueId, landData.typeId())
                val harvestedCrops = landCrops.fold(0) { acc, cur ->
                    if (cur.toLocation(world).block.isEmpty)
                        acc + 1
                    else
                        acc
                }
                if (harvestedCrops < landCrops.size) {
                    remove("plantar_disponivel")
                    remove("plantar_gerando")
                    modify("plantar_bloqueado") {
                        it.formatLore(
                            "{plantacao}" to cropName,
                            "{colhidas}" to harvestedCrops.toString(),
                            "{atuais}" to landCrops.size.toString()
                        )
                    }
                }

                else {
                    remove("plantar_gerando")
                    remove("plantar_bloqueado")
                }
            }
        }
    }
}