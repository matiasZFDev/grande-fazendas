package com.grandemc.fazendas.bukkit.view.land

import com.grandemc.fazendas.config.FarmsConfig
import com.grandemc.fazendas.config.FertilizingConfig
import com.grandemc.fazendas.config.MaterialsConfig
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
    private val materialsConfig: MaterialsConfig
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
                remove("evolucao_upavel")
                remove("evolucao_nao_upavel")
                modify("evolucao_maximo") {
                    it.formatLore(
                        "{nivel}" to landLevel.toString(),
                        "{plantacoes}" to currentLandSchematicData.cropVectors.size.toString()
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
                    remove("evolucao_nao_upavel")
                    remove("evolucao_maximo")
                    modify("evolucao_upavel") {
                        it.formatLore(
                            "{nivel}" to landLevel.toString(),
                            "{novo_nivel}" to landLevel.inc().toString(),
                            "{plantacoes}" to currentLandSchematicData.cropVectors.size.toString(),
                            "{novas_plantacoes}" to nextLevelSchematicData.cropVectors.size.toString(),
                            "{xp}" to landData.xp().dottedFormat(),
                            "{xp_precisa}" to currentLand.evolution.xp.dottedFormat(),
                            "{ouro}" to goldBank.gold(player.uniqueId).toFormat(),
                            "{ouro_preciso}" to currentLand.evolution.gold.toFormat()
                        )
                    }
                }

                else {
                    remove("evolucao_upavel")
                    remove("evolucao_maximo")
                    modify("evolucao_nao_upavel") {
                        it.formatLore(
                            "{nivel}" to landLevel.toString(),
                            "{novo_nivel}" to landLevel.inc().toString(),
                            "{plantacoes}" to currentLandSchematicData.cropVectors.size.toString(),
                            "{novas_plantacoes}" to nextLevelSchematicData.cropVectors.size.toString(),
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
                val fertilizing = fertilizingConfig.get().getById(boostId)
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

            if (landData.cropId() != null) {
                val cropName = materialsConfig.get().getById(landData.cropId()!!).name
                remove("plantar_disponivel")
                modify("plantar_gerando") {
                    it.formatLore(
                        "{plantacao}" to cropName,
                        "{geracao}" to landData.resetCountdown().timeFormat()
                    )
                }
            }

            else {
                remove("plantar_gerando")
            }
        }
    }
}