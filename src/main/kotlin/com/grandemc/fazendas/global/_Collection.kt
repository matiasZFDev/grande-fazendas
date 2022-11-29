package com.grandemc.fazendas.global

fun <T> Collection<T>.cut(drop: Int, take: Int): Collection<T> {
    return drop(drop).take(take)
}