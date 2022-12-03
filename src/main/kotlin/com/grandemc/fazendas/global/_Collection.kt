package com.grandemc.fazendas.global

fun <T> Collection<T>.cut(drop: Int, take: Int): Collection<T> {
    return drop(drop).take(take)
}

fun <E : Any> Collection<E>.firstIndex(action: (Int, E) -> Boolean): Int {
    var index = 0
    return indexOfFirst {
        action(index++, it)
    }
}