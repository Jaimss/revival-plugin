package dev.jaims.revivalplugin.command

import dev.jaims.revivalplugin.RevivalPlugin
import dev.jaims.revivalplugin.config.Config
import dev.jaims.revivalplugin.const.Permission
import dev.jaims.revivalplugin.manager.MessagingManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class ReloadCommand(private val plugin: RevivalPlugin) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        val messagingManager = MessagingManager.of(sender)
        if (!Permission.RELOAD.has(sender)) {
            messagingManager.sendMessage(Config.GENERAL_MESSAGE_NO_PERMISSION.get())
            return true
        }

        plugin.reloadConfig()
        messagingManager.sendMessage(Config.GENERAL_MESSAGE_RELOAD.get())

        return true
    }

}