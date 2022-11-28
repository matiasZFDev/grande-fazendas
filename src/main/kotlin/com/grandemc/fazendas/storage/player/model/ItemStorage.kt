package com.grandemc.fazendas.storage.player.model

import java.util.LinkedList

class ItemStorage(private val items: MutableList<StorageItem> = LinkedList()) {
    fun addItem(id: Byte, amount: Short) {
        items.find { id == it.id }?.let {
            it.amount = (it.amount + amount).toShort()
        } ?: items.add(StorageItem(id, amount))
    }

    fun getAmount(id: Byte): Short {
        return items.find { id == it.id }?.amount ?: 0
    }

    fun hasAmount(id: Byte, amount: Short): Boolean {
        return getAmount(id) >= amount
    }

    fun removeAmount(id: Byte, amount: Short) {
        items.find { id == it.id }?.let {
            if (it.amount - amount < 0)
                return

            it.amount = (it.amount - amount).toShort()
        }
    }

    fun items(): List<StorageItem> = items
}