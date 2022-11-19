package com.grandemc.fazendas.storage.player.model

class FarmHoe(
    private val enchants: List<HoeEnchant>,
    private var collectCount: Double
) {
    fun upgradeEnchant(enchant: CustomEnchant, levels: Short = 1) {
        enchants.find { enchant == it.enchant }!!.let {
            it.level = (it.level + levels).toShort()
        }
    }

    fun enchantLevel(enchant: CustomEnchant): Short {
        return enchants.find { enchant == it.enchant }!!.level
    }

    fun collectCount(): Double = collectCount
}