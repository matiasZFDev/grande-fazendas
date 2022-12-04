package com.grandemc.fazendas.manager.task

interface TaskManager {
    fun runTimer(delay: Long, period: Long, async: Boolean, action: () -> Unit): Int
}