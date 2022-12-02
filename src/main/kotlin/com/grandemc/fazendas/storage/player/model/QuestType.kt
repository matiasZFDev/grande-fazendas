package com.grandemc.fazendas.storage.player.model

enum class QuestType(
    private val configName: String,
    private val id: Byte
) {
    CROP_COLLECT("coleta", 0),
    CRAFT("craft", 1),
    HAND_OVER("entrega", 2),
    XP_EARN("ganhar_xp", 3),
    PLANT("plantar", 4),
    MARKET_SELL("mercado_vender", 5),
    MARKET_POST("mercado_postar", 6),
    MARKET_BUY("mercado_comprar", 7);

    fun configName(): String = configName
    fun id(): Byte = id

    companion object {
        fun fromConfigName(configName: String): QuestType {
            return values().first { it.configName == configName }
        }

        fun fromId(id: Byte): QuestType {
            return values().first { it.id == id }
        }
    }
}