package com.grandemc.fazendas.registry.command

import com.grandemc.fazendas.bukkit.command.xfazendas.ChangeLand
import com.grandemc.fazendas.manager.LandManager
import com.grandemc.fazendas.provider.GlobalMessagesProvider
import com.grandemc.post.external.lib.command.base.CommandCompound
import com.grandemc.post.external.lib.factory.Factory
import com.grandemc.post.external.lib.global.command.buildCommand
import org.bukkit.plugin.java.JavaPlugin

class XFazendasCommands(
    private val plugin: JavaPlugin,
    private val landManager: LandManager
) : Factory<CommandCompound> {
    override fun create(): CommandCompound {
        return buildCommand(plugin, GlobalMessagesProvider.get()) {
            commandName("xfazendas")

            handler {
                noSuchCommandKey("xfazendas")
                bypass(true)

                module("plantiobuildar", ChangeLand(landManager))
            }
        }
    }
}