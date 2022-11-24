package com.grandemc.fazendas.global

import com.sk89q.worldedit.bukkit.BukkitWorld
import com.sk89q.worldedit.world.World
import org.bukkit.Bukkit

fun getWeWorld(worldName: String): World {
    return Bukkit.getWorld(worldName).let(::BukkitWorld)
}