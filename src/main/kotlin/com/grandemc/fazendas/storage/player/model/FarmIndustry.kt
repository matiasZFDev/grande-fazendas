package com.grandemc.fazendas.storage.player.model

class FarmIndustry(private var currentRecipe: FarmRecipe? = null) {
    fun currentRecipe(): FarmRecipe? = currentRecipe
    fun setCurrentRecipe(recipe: FarmRecipe?) {
        currentRecipe = recipe
    }
}