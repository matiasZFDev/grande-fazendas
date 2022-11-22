package com.grandemc.fazendas.util

class Lazy<T>(private val provider: () -> T) {
    fun get(): T = provider()
}

fun <T> lazyValue(provider: () -> T): Lazy<T> = Lazy(provider)