package dev.jaims.revivalplugin.listener

import dev.jaims.revivalplugin.RevivalPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeathListener(private val plugin: RevivalPlugin) : Listener {

    /**
     * Handle the Player Death Event
     */
    @EventHandler
    fun PlayerDeathEvent.onPlayerDeath() {

    }

}