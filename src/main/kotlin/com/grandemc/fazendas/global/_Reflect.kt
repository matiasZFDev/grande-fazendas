package com.grandemc.fazendas.global

inline fun <reified T> Any.member(name: String): T {
    return this::class.java.getDeclaredField(name).let {
        it.isAccessible = true
        it.get(this) as T
    }
}