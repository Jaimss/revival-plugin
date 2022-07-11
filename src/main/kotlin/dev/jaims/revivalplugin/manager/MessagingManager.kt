package dev.jaims.revivalplugin.manager

import dev.jaims.revivalplugin.RevivalPlugin
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class MessagingManager<T : CommandSender> private constructor(private val target: T) {

    companion object {
        fun of(target: Player) = MessagingManager(target)
        fun of(target: CommandSender) = MessagingManager(target)

        private val AUDIENCE = BukkitAudiences.create(JavaPlugin.getPlugin(RevivalPlugin::class.java))
    }

    private val audience by lazy {
        when (target) {
            is Player -> AUDIENCE.player(target)
            else -> AUDIENCE.sender(target)
        }
    }

    private fun sendMessage(component: Component): MessagingManager<T> {
        audience.sendMessage(component)
        return this
    }

    fun sendMessage(message: String, transform: (String) -> String = { it }) =
        sendMessage(transform(message).asComponent())

    private fun String.asComponent() = LegacyComponentSerializer.legacyAmpersand()
        .toBuilder()
        .extractUrls()
        .hexColors()
        .build()
        .deserialize(this)
}