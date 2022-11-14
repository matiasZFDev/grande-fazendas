package com.grandemc.fazendas.provider

abstract class GlobalProvider<T> {
    private var data: T? = null

    fun provide(data: T) {
        this.data = data
    }

    fun get(): T {
        return data ?: throw Error("From ${this::class.simpleName}: Data has not been provided yet.")
    }
}