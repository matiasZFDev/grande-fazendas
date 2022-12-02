package com.grandemc.fazendas.global

inline fun <reified T : Any> Any.asType(): T {
    return this as T
}