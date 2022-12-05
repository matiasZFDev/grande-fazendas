package com.grandemc.fazendas.storage.player.model

enum class FarmUpgradeType(
    private val configName: String,
    private val databaseName: String
) {
    XP_BOOST("mais_xp", "xp_boost") {
        override fun format(value: Double): String {
            return "x$value"
        }
    },
    DAILY_QUESTS("mais_diarias", "daily_quests") {
        override fun format(value: Double): String {
            return "+$value"
        }
    },
    CRAFT_TIME_REDUCTION("menor_tempo_craft", "craft_time_reduction") {
        override fun format(value: Double): String {
            return "-$value%"
        }
    },
    LOCAL_SELL_BONUS("bonus_venda_local", "local_sell_bonus") {
        override fun format(value: Double): String {
            return "+$value%"
        }
    };

    companion object {
        val values: List<FarmUpgradeType> = values().toList()

        fun fromConfigName(configName: String): FarmUpgradeType {
            return values.first { it.configName() == configName }
        }
    }

    fun configName(): String = configName
    fun databaseName(): String = databaseName
    abstract fun format(value: Double): String
}