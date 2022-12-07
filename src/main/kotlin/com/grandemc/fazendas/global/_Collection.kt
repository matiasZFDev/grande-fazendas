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

fun <E : Any> Collection<E>.rollUntil(action: (E) -> Boolean): E {
    var value: E? = find(action)

    while (value == null)
        value = find(action)

    return value
}

fun <E : Any> Collection<E>.rollUntil(rollLimit: Int, action: (E) -> Boolean): E? {
    var value: E? = find(action)
    var rollCount = 1

    while (value == null && rollCount < rollLimit) {
        value = find(action)
        rollCount++
    }

    return value
}