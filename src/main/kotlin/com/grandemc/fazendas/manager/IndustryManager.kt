package com.grandemc.fazendas.manager

import com.grandemc.fazendas.config.IndustryConfig
import com.grandemc.fazendas.storage.player.model.FarmRecipe
import java.util.UUID

class IndustryManager(
    private val farmManager: FarmManager,
    private val storageManager: StorageManager,
    private val industryConfig: IndustryConfig,
    private val statsManager: StatsManager
) {
    fun isBaking(playerId: UUID): Boolean {
        return currentRecipe(playerId) != null
    }

    fun currentRecipe(playerId: UUID): FarmRecipe? {
        return farmManager.farm(playerId).industry().currentRecipe()
    }

    fun startRecipe(playerId: UUID, recipe: IndustryConfig.IndustryRecipe) {
        val bakeTime = statsManager.craftTime(playerId, recipe.bakeTime)
        farmManager.farm(playerId).industry().setCurrentRecipe(
            FarmRecipe(
                recipe.id,
                bakeTime
            )
        )
    }

    fun canCraft(playerId: UUID, recipe: IndustryConfig.IndustryRecipe): Boolean {
        return farmManager.farm(playerId).level() >= recipe.islandLevel &&
                recipe.items.all {
                    val id = storageManager.materialId(it.name)
                    storageManager.has(playerId, id, it.amount)
                }
    }


    fun canCraft(playerId: UUID, recipeId: Byte): Boolean {
        return canCraft(playerId, industryConfig.get().getById(recipeId))
    }

    fun collectRecipe(playerId: UUID): FarmRecipe {
        val recipe = farmManager.farm(playerId).industry().currentRecipe()!!
        farmManager.farm(playerId).industry().setCurrentRecipe(null)
        return recipe
    }
}