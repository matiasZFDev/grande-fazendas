package com.grandemc.fazendas.manager.model

interface Keyable<K, V> {
    fun key(): K
    fun value(): V
}