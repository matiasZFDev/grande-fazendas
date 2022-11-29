package com.grandemc.fazendas.manager.model

import com.grandemc.fazendas.storage.market.model.MarketItem

enum class MarketFilter(private val configName: String) {
    GREATER_AMOUNT("filtro_maior_quantia") {
        override fun apply(model: Collection<MarketItem>): Collection<MarketItem> {
            return model.sortedByDescending(MarketItem::amount)
        }

        override fun opposite(): MarketFilter {
            return LOWER_AMOUNT
        }

        override fun next(): MarketFilter {
            return GREATER_PRICE
        }
    },
    LOWER_AMOUNT("filtro_menor_quantia") {
        override fun apply(model: Collection<MarketItem>): Collection<MarketItem> {
            return model.sortedBy(MarketItem::amount)
        }

        override fun opposite(): MarketFilter {
            return GREATER_AMOUNT
        }

        override fun next(): MarketFilter {
            return GREATER_PRICE
        }
    },
    GREATER_PRICE("filtro_maior_preco") {
        override fun apply(model: Collection<MarketItem>): Collection<MarketItem> {
            return model.sortedByDescending(MarketItem::goldPrice)
        }

        override fun opposite(): MarketFilter {
            return LOWER_PRICE
        }

        override fun next(): MarketFilter {
            return BEST_PRICE
        }
    },
    LOWER_PRICE("filtro_menor_preco") {
        override fun apply(model: Collection<MarketItem>): Collection<MarketItem> {
            return model.sortedBy(MarketItem::goldPrice)
        }

        override fun opposite(): MarketFilter {
            return GREATER_PRICE
        }

        override fun next(): MarketFilter {
            return BEST_PRICE
        }
    },
    BEST_PRICE("filtro_melhor_preco") {
        override fun apply(model: Collection<MarketItem>): Collection<MarketItem> {
            return model.sortedByDescending { it.amount / it.goldPrice }
        }

        override fun opposite(): MarketFilter {
            return WORST_PRICE
        }

        override fun next(): MarketFilter {
            return MOST_RECENT
        }
    },
    WORST_PRICE("filtro_pior_preco") {
        override fun apply(model: Collection<MarketItem>): Collection<MarketItem> {
            return model.sortedBy { it.amount / it.goldPrice }
        }

        override fun opposite(): MarketFilter {
            return BEST_PRICE
        }

        override fun next(): MarketFilter {
            return MOST_RECENT
        }
    },
    MOST_RECENT("filtro_mais_recente") {
        override fun apply(model: Collection<MarketItem>): Collection<MarketItem> {
            return model.sortedByDescending(MarketItem::expiryTime)
        }

        override fun opposite(): MarketFilter {
            return LEAST_RECENT
        }

        override fun next(): MarketFilter {
            return GREATER_AMOUNT
        }
    },
    LEAST_RECENT("filtro_menos_recente") {
        override fun apply(model: Collection<MarketItem>): Collection<MarketItem> {
            return model.sortedBy(MarketItem::expiryTime)
        }

        override fun opposite(): MarketFilter {
            return MOST_RECENT
        }

        override fun next(): MarketFilter {
            return GREATER_AMOUNT
        }
    };

    fun configName(): String = configName
    abstract fun apply(model: Collection<MarketItem>): Collection<MarketItem>
    abstract fun opposite(): MarketFilter
    abstract fun next(): MarketFilter
}