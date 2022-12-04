package com.grandemc.fazendas.manager.task

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class TaskManagerImpl(private val plugin: Plugin) : TaskManager {
    override fun runTimer(delay: Long, period: Long, async: Boolean, action: () -> Unit): Int {
        return if (async)
            Bukkit.getScheduler().runTaskTimerAsynchronously(
                plugin, action, delay, period
            ).taskId
        else
            Bukkit.getScheduler().runTaskTimer(
                plugin, action, delay, period
            ).taskId
    }
}