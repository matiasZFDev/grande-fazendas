package com.grandemc.fazendas.util

interface Checkable<T> {
    fun values(): Iterable<T>
    fun contains(value: T): Boolean
}