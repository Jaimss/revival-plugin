package dev.jaims.revivalplugin.listener

import dev.jaims.revivalplugin.RevivalPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent

class PlayerRespawnListener(private val plugin: RevivalPlugin) : Listener {

    /**
     * Handle the Player Respawn Event
     */
    @EventHandler
    fun PlayerRespawnEvent.onPlayerRespawn() {

    }

}