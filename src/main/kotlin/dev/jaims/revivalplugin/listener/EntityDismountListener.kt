package dev.jaims.revivalplugin.listener

import dev.jaims.revivalplugin.RevivalPlugin
import dev.jaims.revivalplugin.const.PlayerState
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.spigotmc.event.entity.EntityDismountEvent

class EntityDismountListener(private val plugin: RevivalPlugin) : Listener {

    @EventHandler
    fun EntityDismountEvent.onDismount() {
        if (entity !is Player) return

        val state = plugin.playerStateManager.getState(entity.uniqueId)
        if (state != PlayerState.REVIVABLE) return

        // can't dismount
        isCancelled = true
    }

}