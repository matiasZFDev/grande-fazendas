package com.grandemc.fazendas.config.model.level.enchant

class EnchantLevel(val requirements: Requirements, val upgrades: Upgrades) {
    class Requirements(
        val gold: Double
    )

    class Upgrades(
        val value: Double
    )
}